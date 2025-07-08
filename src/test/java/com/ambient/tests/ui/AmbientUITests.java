package com.ambient.tests.ui;

import com.ambient.AbstractTest;
import com.ambient.ui.models.AppointmentRequest;
import com.ambient.ui.pages.AmbientLandingPage;
import com.ambient.utils.config.DateTimeHelper;
import com.ambient.utils.config.NameHelper;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

/**
 * UI Test suite for validating core functionality of the Ambient web application.
 *
 * <p>Focuses on appointment creation workflows and uses the shared base test setup from {@link AbstractTest}.</p>
 */
@Epic("Ambient UI Validation")
@Feature("Core UI Testing")
@Owner("UI Testing Team")
@Slf4j
public class AmbientUITests extends AbstractTest {

    /**
     * Smoke and regression test to verify the successful creation of a new appointment via the UI.
     *
     * <p>This test fills in a form with randomized patient data and confirms that the appointment is created
     * and reflected in the UI.</p>
     */
    @Test(groups = {"smoke", "regression"})
    @Story("New Appointment Creation")
    @Description("Verify successful creation of new appointment")
    @Severity(SeverityLevel.BLOCKER)
    public void createAppointmentTest() {

        // Generate appointment data
        String startTime = DateTimeHelper.getCurrentDateTimeInIsoFormat();
        String endTime = DateTimeHelper.getDateTimePlus30MinutesInIsoFormat();
        String fakePatientName = NameHelper.generateRandomFullName();
        String notes = NameHelper.generateNotes();

        // Build AppointmentRequest object using builder pattern
        AppointmentRequest appointmentRequest = new AppointmentRequest()
                .builder()
                .patientName(fakePatientName)
                .startTime(startTime)
                .endTime(endTime)
                .notes(notes)
                .build();

        // Execute UI workflow: open landing page -> create new appointment -> verify it -> return
        web()
                .getLandingPage(AmbientLandingPage.class)
                .clickOnNewButton()
                .createAppointment(appointmentRequest)
                .validateAppointmentConfirmed(appointmentRequest.getPatientName())
                .clickOnBackButton();
    }
}
