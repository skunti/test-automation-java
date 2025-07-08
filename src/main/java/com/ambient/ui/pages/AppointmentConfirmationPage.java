package com.ambient.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.ambient.ui.WebUser.getPage;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Page Object representing the Appointment Confirmation Page.
 * This page appears after successfully creating a new appointment.
 */
public class AppointmentConfirmationPage extends BasePage {

    /**
     * WebElement representing the "State: completed" text to verify the appointment status.
     */
    @FindBy(xpath = "//p[text()='State: completed']")
    private WebElement stateCompleted;

    /**
     * WebElement representing the back button to navigate to the previous page.
     */
    @FindBy(xpath = "//button[normalize-space(text()) = '← Back']")
    private WebElement backButton;

    /**
     * Constructor for AppointmentConfirmationPage.
     * Waits for the mandatory elements to be visible before interacting with the page.
     *
     * @param driver the WebDriver instance
     */
    public AppointmentConfirmationPage(WebDriver driver) {
        super(driver);
        // Wait until the "State: completed" element is visible, ensuring the page is loaded
        loadPage(List.of(stateCompleted));
    }

    /**
     * Validates that the appointment confirmation page shows the patient's name.
     * Checks that the heading with the patient's name is visible on the page.
     *
     * @param patientName the expected patient name to validate on the page
     * @return the current AppointmentConfirmationPage instance for method chaining
     */
    public AppointmentConfirmationPage validateAppointmentConfirmed(String patientName) {
        // Locate the heading element with the patient's name dynamically
        By nameHeadingBy = By.xpath(String.format("//h1[text()='%s']", patientName));
        // Wait for the heading element to be displayed before asserting
        waitForElementToBeDisplayed(nameHeadingBy, 10, 1);

        WebElement nameHeading = driver.findElement(nameHeadingBy);

        // Assert the heading is visible to confirm appointment success
        assertThat(nameHeading.isDisplayed())
                .as(String.format("The heading '%s' is not visible on the screen.", patientName))
                .isTrue();

        return this;
    }

    /**
     * Clicks the back button to navigate back to the New Appointment Page.
     *
     * @return a new instance of NewAppointmentPage
     */
    public NewAppointmentPage clickOnBackButton() {
        click(backButton);
        return getPage(NewAppointmentPage.class);
    }
}
