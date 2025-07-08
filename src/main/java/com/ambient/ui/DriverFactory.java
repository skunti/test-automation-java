package com.ambient.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * DriverFactory manages the lifecycle of thread-safe WebDriver instances.
 */
public class DriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Initializes the WebDriver instance for the current thread.
     * Currently supports only ChromeDriver with optional headless configuration.
     */
    private static void initializeDriver() {
        if (driver.get() == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            // Optional: Enable headless mode if needed
            if (Boolean.getBoolean("headless")) {
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
            }

            driver.set(new ChromeDriver(options));
        }
    }

    /**
     * Returns the thread-local WebDriver instance, initializing if necessary.
     */
    public static WebDriver getDriver() {
        initializeDriver();
        return driver.get();
    }

    /**
     * Quits the WebDriver and removes it from thread-local storage.
     */
    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove();
        }
    }
}
