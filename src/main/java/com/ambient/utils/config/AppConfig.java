package com.ambient.utils.config;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for loading configuration values from the {@code config.properties} file.
 * <p>
 * This class loads the properties at class initialization time and provides convenient
 * methods to access configuration values used in both API and UI testing layers.
 */
@Slf4j
public class AppConfig {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();

    // Static block to initialize properties when the class is loaded
    static {
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                log.error("Configuration file '{}' not found in classpath.", CONFIG_FILE);
                throw new RuntimeException("Missing configuration file: " + CONFIG_FILE);
            }
            properties.load(input);
            log.info("Configuration loaded successfully from '{}'", CONFIG_FILE);
        } catch (IOException e) {
            log.error("Failed to load configuration file '{}'", CONFIG_FILE, e);
            throw new RuntimeException("Error loading configuration from " + CONFIG_FILE, e);
        }
    }

    /**
     * Retrieves the base URL used for Ambient API testing.
     *
     * @return The base API URL defined in {@code config.properties}
     */
    @Step("Get API base URL from config")
    public static String getBaseApiUrl() {
        return getProperty("ambient.api.baseurl");
    }

    /**
     * Retrieves the base URL used for Ambient UI testing.
     *
     * @return The base UI URL defined in {@code config.properties}
     */
    @Step("Get UI base URL from config")
    public static String getBaseUiUrl() {
        return getProperty("ambient.ui.baseurl");
    }

    /**
     * Retrieves a property by key from the loaded configuration file.
     *
     * @param key the property key
     * @return the property value, or {@code null} if not found
     */
    private static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            log.warn("Configuration key '{}' is missing or empty in '{}'", key, CONFIG_FILE);
        }
        return value;
    }
}
