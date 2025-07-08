package com.ambient.api.services;

import com.ambient.api.models.Appointments;
import io.qameta.allure.Step;

import static com.ambient.api.client.RestCall.getRequest;

/**
 * Service class to interact with Appointment-related API endpoints.
 */
public class AppointmentService {

    /**
     * Default constructor.
     */
    public AppointmentService() {
    }

    /**
     * Retrieves all appointments by making a GET request to the /v1/appointments/query endpoint.
     * The method is annotated with Allure's @Step for better reporting.
     *
     * @return an Appointments object containing the list of appointments,
     *         or null if the request fails
     */
    @Step("Get all appointments")
    public Appointments getQuery() {
        try {
            // Perform GET request and deserialize response to Appointments class
            return getRequest("/v1/appointments/query", Appointments.class);
        } catch (Exception e) {
            // Log error and return null in case of failure
            System.err.println("Failed to retrieve appointments: " + e.getMessage());
            return null;
        }
    }
}
