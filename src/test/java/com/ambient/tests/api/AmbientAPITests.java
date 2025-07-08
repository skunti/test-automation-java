package com.ambient.tests.api;

import com.ambient.AbstractTest;
import com.ambient.api.models.Appointments;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * API Test suite for validating the core functionalities of the Ambient backend service.
 *
 * <p>Includes coverage for appointment data retrieval through API queries.</p>
 */
@Epic("Ambient API Validation")
@Feature("Core API Testing")
@Owner("API Testing Team")
@Slf4j
public class AmbientAPITests extends AbstractTest {

    /**
     * Smoke and regression test to verify the successful retrieval of all appointments via the API.
     *
     * <p>The test checks that the response is not null and contains at least one appointment.</p>
     */
    @Test(groups = {"smoke", "regression"})
    @Story("Appointment Retrievals")
    @Description("Verify successful retrieval of all appointments")
    @Severity(SeverityLevel.BLOCKER)
    public void getAppointmentsQueryTest() {
        try {
            // Step: Retrieve all appointments via API
            Appointments appointments = Allure.step("Get appointments", () ->
                    api().getAppointmentService().getQuery()
            );

            // Step: Validate the response contains data
            Allure.step("Validate response", () -> {
                assertNotNull(appointments, "Appointments list should not be null");
                assertFalse(appointments.getItems().isEmpty(), "Appointments list should not be empty");
            });

        } catch (Exception e) {
            // Log and fail the test if any exception occurs
            log.error("Failed to retrieve appointments", e);
            fail("Exception during getAppointmentsQueryTest: " + e.getMessage());
        }
    }
}
