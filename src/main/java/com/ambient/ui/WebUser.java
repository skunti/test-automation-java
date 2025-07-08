package com.ambient.ui;

import com.ambient.ui.pages.BasePage;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;

import static com.ambient.utils.config.AppConfig.getBaseApiUrl;
import static com.ambient.utils.config.AppConfig.getBaseUiUrl;

/**
 * WebUser manages WebDriver lifecycle and page navigation for UI testing.
 * It supports thread-safe driver instantiation, URL navigation, and page object retrieval.
 */
public class WebUser {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private WebUser() {
        // Private constructor to enforce usage through build()
    }

    /**
     * Initializes WebDriver for the current thread and returns a WebUser instance.
     */
    public static WebUser build() {
        driver.set(DriverFactory.getDriver());
        return new WebUser();
    }

    /**
     * Returns the thread-local WebDriver instance.
     */
    public static WebDriver getDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver == null) {
            throw new IllegalStateException("WebDriver has not been initialized. Call WebUser.build() first.");
        }
        return webDriver;
    }

    /**
     * Gracefully quits the WebDriver and removes it from the thread-local storage.
     */
    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove();
        }
    }

    /**
     * Opens the base UI or API page depending on parameters and returns the given Page Object.
     *
     * @param page       Class of the Page Object to instantiate.
     * @param parameters Optional path segment(s) for the API base URL.
     * @param <T>        Type of the Page Object.
     */
    public <T extends BasePage> T getLandingPage(Class<T> page, Object... parameters) {
        if (parameters.length > 0) {
            navigateTo(String.format(getBaseApiUrl() + "%s", parameters));
        } else {
            navigateTo(getBaseUiUrl());
        }
        return getPage(page);
    }

    /**
     * Navigates the browser to a given URL.
     *
     * @param url Fully qualified URL.
     */
    public WebUser navigateTo(String url) {
        getDriver().navigate().to(url);
        return this;
    }

    /**
     * Returns a new instance of the requested Page Object, passing the current WebDriver.
     *
     * @param pageClass Page Object class to instantiate.
     * @param <T>       Type of the Page Object.
     */
    public static <T extends BasePage> T getPage(Class<T> pageClass) {
        if (pageClass == null) return null;
        try {
            return pageClass.getConstructor(WebDriver.class).newInstance(getDriver());
        } catch (InstantiationException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Failed to instantiate Page Object: " + pageClass.getSimpleName()
                    + ". Current URL: " + getDriver().getCurrentUrl(), e);
        }
    }
}
