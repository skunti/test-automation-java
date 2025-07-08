package com.ambient.api.client;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract base class for REST API interactions using RestAssured.
 * Sets up the base URL, configures request/response logging,
 * and applies Allure filters for enhanced reporting.
 */
@Slf4j
public abstract class RestCall {

    protected static String baseUrl;

    /**
     * Initializes the RestCall with a base URL and configures RestAssured settings.
     *
     * @param baseUrl Base URL of the API (e.g., https://api.example.com)
     */
    protected RestCall(String baseUrl) {
        RestCall.baseUrl = baseUrl;
        RestAssured.filters(new AllureRestAssured());  // Attach Allure reporting filter
        RestAssured.baseURI = baseUrl;                  // Set base URI for requests
        configureRestAssured();
    }

    /**
     * Configures RestAssured to enable detailed logging of request and response
     * if the validation fails, helping with debugging test failures.
     */
    private void configureRestAssured() {
        RestAssured.config = RestAssured.config()
                .logConfig(io.restassured.config.LogConfig.logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails());
    }

    /**
     * Sends a GET request to the given API endpoint and maps the response body
     * into an instance of the specified class type.
     *
     * Logs request and response details for better traceability.
     *
     * @param endpoint     Relative URL endpoint (e.g., "/v1/appointments")
     * @param responseType Class type to deserialize the JSON response into
     * @param <T>          Type of the response object
     * @return Deserialized response object if status code is 2xx; otherwise, returns null
     */
    @Step("GET request to endpoint: {endpoint}")
    public static <T> T getRequest(String endpoint, Class<T> responseType) {
        try {
            String fullUrl = baseUrl + endpoint;

            log.info("\n==================== [API CALL] ====================");
            log.info("→ Calling GET Request");
            log.info("→ Endpoint: {}", fullUrl);
            log.info("====================================================");

            Response response = RestAssured
                    .given()
                    .when()
                    .log().all() // Log full request details
                    .get(endpoint);

            log.info("\n================== [API RESPONSE] ==================");
            log.info("← Status Code: {}", response.getStatusCode());
            String prettyJson = response.getBody().asPrettyString();
            log.info("← Body:\n{}", prettyJson);
            log.info("====================================================");

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                return response.as(responseType);
            } else {
                log.error("GET request to {} failed with status code: {}", endpoint, response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            log.error("Exception during GET request to {}: {}", endpoint, e.getMessage());
            return null;
        }
    }
}
