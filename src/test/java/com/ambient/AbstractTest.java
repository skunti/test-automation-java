package com.ambient;

import com.ambient.api.client.Services;
import com.ambient.ui.WebUser;
import org.testng.annotations.AfterMethod;

/**
 * Base test class providing common functionality for both API and UI test setups.
 *
 * <p>Includes lazy initialization of API service and web UI helpers,
 * and ensures cleanup after each test method execution.</p>
 */
public class AbstractTest {

    /** Thread-safe instance of API service client */
    protected ThreadLocal<Services> api;

    /** Instance of the web UI helper */
    protected WebUser web;

    /**
     * Provides a lazily initialized instance of the {@link WebUser} helper.
     *
     * @return the active {@link WebUser} instance
     */
    public WebUser web() {
        // Build a new WebUser if it's null or the driver is no longer available
        if (web == null || WebUser.getDriver() == null) {
            web = WebUser.build();
        }
        return web;
    }

    /**
     * Provides a thread-safe, lazily initialized instance of the {@link Services} API client.
     *
     * @return the {@link Services} instance for the current thread
     */
    public Services api() {
        // Initialize thread-local Services instance if needed
        if (api == null || api.get() == null) {
            api = ThreadLocal.withInitial(Services::new);
        }
        return api.get();
    }

    /**
     * Cleans up resources after each test method execution.
     * <p>This includes clearing the thread-local API service and quitting the web driver.</p>
     */
    @AfterMethod(alwaysRun = true)
    public final void afterMethod() {
        // Clear thread-local API service
        if (api != null) {
            api = null;
        }

        // Quit the driver and nullify WebUser instance
        if (web != null) {
            WebUser.quitDriver();
            web = null;
        }
    }
}
