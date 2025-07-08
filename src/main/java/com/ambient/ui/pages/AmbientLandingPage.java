package com.ambient.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.ambient.ui.WebUser.getPage;

/**
 * Page Object representing the Ambient Landing Page.
 * This is typically the first page after login or landing in the app.
 */
public class AmbientLandingPage extends BasePage {

    /**
     * WebElement representing the "+ New" button
     * used to initiate creation of a new appointment.
     */
    @FindBy(xpath = "//button[normalize-space(text()) = '+ New']")
    private WebElement newButton;

    /**
     * Constructor for AmbientLandingPage.
     * Waits for the "+ New" button to be visible before interacting.
     *
     * @param driver the WebDriver instance
     */
    public AmbientLandingPage(WebDriver driver) {
        super(driver);
        // Wait until the "+ New" button is visible on the landing page
        loadPage(List.of(newButton));
    }

    /**
     * Clicks the "+ New" button to navigate to the New Appointment Page.
     *
     * @return a new instance of NewAppointmentPage
     */
    public NewAppointmentPage clickOnNewButton() {
        click(newButton);
        return getPage(NewAppointmentPage.class);
    }
}
