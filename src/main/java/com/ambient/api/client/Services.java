package com.ambient.api.client;

import com.ambient.api.services.AppointmentService;
import com.ambient.utils.config.AppConfig;

/**
 * Centralized API client class that provides access to various service classes.
 * Extends RestCall to inherit HTTP request capabilities.
 */
public class Services extends RestCall {

    private AppointmentService appointmentService;

    /**
     * Initializes the Services class with the base API URL from the application configuration.
     */
    public Services() {
        super(AppConfig.getBaseApiUrl());
    }

    /**
     * Provides a singleton instance of AppointmentService.
     * Initializes the service lazily on first call.
     *
     * @return the AppointmentService instance
     */
    public AppointmentService getAppointmentService() {
        if (appointmentService == null) {
            appointmentService = new AppointmentService();
        }
        return appointmentService;
    }

    /**
     * Static factory method to obtain a new instance of Services.
     *
     * @return a new Services instance
     */
    public static Services api() {
        return new Services();
    }
}
