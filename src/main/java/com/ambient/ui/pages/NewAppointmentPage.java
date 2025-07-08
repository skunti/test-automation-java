package com.ambient.ui.pages;

import com.ambient.ui.models.AppointmentRequest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.ambient.ui.WebUser.getPage;

/**
 * Page Object representing the "New Appointment" screen in the Ambient web application.
 * This class encapsulates UI interactions needed to create a new appointment.
 */
public class NewAppointmentPage extends BasePage {

    // Input field for entering the patient's name
    @FindBy(css = "input[type='text']")
    private WebElement patientNameInput;

    // Text area for entering additional notes
    @FindBy(css = "textarea")
    private WebElement notesInput;

    // Input field for specifying appointment start time
    @FindBy(xpath = "//span[text()='Start Time']/following-sibling::input")
    private WebElement startTimeInput;

    // Input field for specifying appointment end time
    @FindBy(xpath = "//span[text()='End Time']/following-sibling::input")
    private WebElement endTimeInput;

    // Button to submit and create the appointment
    @FindBy(xpath = "//button[normalize-space(text()) = 'Create Appointment']")
    private WebElement createAppointmentButton;

    /**
     * Constructor for the NewAppointmentPage.
     *
     * @param driver WebDriver instance used for interacting with the browser.
     */
    public NewAppointmentPage(WebDriver driver) {
        super(driver);
        // Wait until the 'Create Appointment' button is visible to ensure the page has loaded
        loadPage(List.of(createAppointmentButton));
    }

    /**
     * Fills in the appointment form with the provided details and submits it.
     *
     * @param appointmentRequest an AppointmentRequest object containing all required appointment details.
     * @return an AppointmentConfirmationPage object representing the confirmation screen.
     */
    public AppointmentConfirmationPage createAppointment(AppointmentRequest appointmentRequest) {
        // Fill in form fields using data from the appointment request
        sendKeys(patientNameInput, appointmentRequest.getPatientName());
        sendKeys(startTimeInput, appointmentRequest.getStartTime());
        sendKeys(endTimeInput, appointmentRequest.getEndTime());
        sendKeys(notesInput, appointmentRequest.getNotes());

        // Click the 'Create Appointment' button to submit the form
        click(createAppointmentButton);

        // Return the next page in the workflow: AppointmentConfirmationPage
        return getPage(AppointmentConfirmationPage.class);
    }

    /**
     * Clears all form fields on the New Appointment page.
     * Useful for test scenarios where the form needs to be reset.
     */
    public void clearForm() {
        patientNameInput.clear();
        startTimeInput.clear();
        endTimeInput.clear();
        notesInput.clear();
    }
}
