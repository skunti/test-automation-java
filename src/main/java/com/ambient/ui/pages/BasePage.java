package com.ambient.ui.pages;

import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

/**
 * BasePage serves as a foundational class for all page objects.
 * It provides common WebDriver utilities like element waits, interactions,
 * JavaScript executions, and page load synchronizations.
 */
public class BasePage {
    private static final long TIMEOUT = 60;
    protected WebDriver driver;

    /**
     * Constructor initializes the page object and elements using PageFactory.
     *
     * @param driver the WebDriver instance driving the browser
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Waits for the specified list of mandatory elements to be visible on the page.
     * This method ensures the page has loaded by checking presence of critical UI elements.
     *
     * @param mandatoryElements list of WebElements that must be visible
     */
    protected void loadPage(List<WebElement> mandatoryElements) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        for (WebElement elm : mandatoryElements) {
            try {
                wait.ignoring(StaleElementReferenceException.class)
                        .withTimeout(Duration.ofSeconds(TIMEOUT))
                        .pollingEvery(Duration.ofSeconds(1))
                        .until(visibilityOf(elm));
            } catch (TimeoutException e) {
                throw new RuntimeException("Timeout waiting for element visibility: " + elm, e);
            }
        }
    }

    /**
     * Clears any existing text and sends the specified keys to the element.
     *
     * @param element the WebElement to send keys to
     * @param value   the string value to enter
     */
    protected void sendKeys(@NonNull WebElement element, @NonNull String value) {
        element.clear();
        element.sendKeys(value);
    }

    /**
     * Clicks on the specified WebElement after logging.
     *
     * @param element the WebElement to click
     */
    protected void click(@NonNull WebElement element) {
        log(element);
        element.click();
    }

    /**
     * Clicks on the element located by the given By selector.
     *
     * @param by the locator of the element to click
     */
    protected void click(@NonNull By by) {
        click(driver.findElement(by));
    }

    /**
     * Executes a JavaScript click on the given element.
     * Useful when regular Selenium click fails due to overlay or other issues.
     *
     * @param element the WebElement to click using JS
     */
    public void javaScriptClick(@NonNull WebElement element) {
        log(element);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
        executor.executeScript("arguments[0].click();", element);
    }

    /**
     * Selects a dropdown option by visible text, ignoring case sensitivity.
     *
     * @param selectElement the dropdown WebElement
     * @param text          the visible text to select (case-insensitive)
     */
    public void selectDropDownByTextIgnoringCase(@NonNull WebElement selectElement, @NonNull String text) {
        Select dropdown = new Select(selectElement);
        for (WebElement option : dropdown.getOptions()) {
            if (option.getText().equalsIgnoreCase(text)) {
                dropdown.selectByVisibleText(option.getText());
                break;
            }
        }
    }

    /**
     * Waits until all specified elements are visible.
     *
     * @param mandatoryElements list of WebElements to wait for visibility
     */
    protected void waitForWebElements(List<WebElement> mandatoryElements) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        for (WebElement elm : mandatoryElements) {
            try {
                wait.ignoring(StaleElementReferenceException.class)
                        .withTimeout(Duration.ofSeconds(60))
                        .pollingEvery(Duration.ofSeconds(1))
                        .until(visibilityOf(elm));
            } catch (TimeoutException e) {
                throw new RuntimeException("Timeout waiting for element visibility: " + elm, e);
            }
        }
    }

    /**
     * Focuses on the given element using JavaScript.
     *
     * @param element the WebElement to focus on
     */
    public void javaScriptFocusOnElement(@NonNull WebElement element) {
        log(element);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].focus();", element);
    }

    /**
     * Waits for an element to be displayed using a fluent wait strategy.
     *
     * @param by           the locator of the element
     * @param timeout      timeout in seconds to wait
     * @param pollInterval polling interval in seconds
     */
    public void waitForElementToBeDisplayed(@NonNull By by, int timeout, int pollInterval) {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(pollInterval))
                .ignoring(NoSuchElementException.class)
                .until(driver -> driver.findElement(by).isDisplayed());
    }

    /**
     * Waits for a loading spinner element to appear and then disappear,
     * indicating page load or data fetch completion.
     */
    public void waitForPage() {
        By spinnerBy = By.cssSelector("[data-fetching]");
        try {
            presenceOfElementLocated(spinnerBy, 5);
            waitForAttributeValueToChange(spinnerBy, "data-fetching", "false");
        } catch (TimeoutException te) {
            // Spinner not found, continue normally
        }
    }

    /**
     * Waits for an element to be present in the DOM.
     *
     * @param by               locator of the element
     * @param timeoutInSeconds  time to wait before timing out
     */
    public void presenceOfElementLocated(@NonNull By by, int timeoutInSeconds) {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.presenceOfElementLocated(by));
        driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
    }

    /**
     * Waits until a specified attribute of an element has a certain value.
     *
     * @param by                    locator of the element
     * @param attribute              the attribute to check
     * @param expectedAttributeValue expected value of the attribute
     */
    public void waitForAttributeValueToChange(@NonNull By by, @NonNull String attribute, @NonNull String expectedAttributeValue) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .ignoring(WebDriverException.class)
                .until(ExpectedConditions.attributeToBe(by, attribute, expectedAttributeValue));
    }

    /**
     * Removes focus from the currently active element using JavaScript blur.
     */
    public void blur() {
        ((JavascriptExecutor) driver).executeScript("!!document.activeElement ? document.activeElement.blur() : 0");
    }

    /**
     * Waits for the specified text to be present in the given element.
     *
     * @param element locator of the element
     * @param text    the expected text to be present
     */
    public void textToBePresentInElement(@NonNull By element, @NonNull String text) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBePresentInElementLocated(element, text));
    }

    /**
     * Pauses the execution for the specified number of seconds.
     *
     * @param seconds number of seconds to pause
     */
    public static void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Logs information about the WebElement being interacted with.
     * Attempts to use various attributes to identify the element meaningfully.
     *
     * @param element the WebElement being logged
     */
    private void log(@NonNull WebElement element) {
        String display = "link";
        if (element.getAttribute("text") != null)
            display = element.getAttribute("text");
        else if (element.getAttribute("data-auto") != null)
            display = element.getAttribute("data-auto");
        else if (element.getAttribute("name") != null)
            display = element.getAttribute("name");
        else if (element.getAttribute("class") != null)
            display = element.getAttribute("class");
        // log.info(String.format("Clicking On: %s", display));
    }
}
