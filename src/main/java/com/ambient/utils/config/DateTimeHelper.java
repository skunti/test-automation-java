package com.ambient.utils.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for generating ISO-formatted date and time strings.
 *
 * <p>Useful for scheduling and time-based test inputs in UI and API automation scenarios.</p>
 */
public class DateTimeHelper {

    // Formatter for ISO-like pattern without seconds (e.g., 2025-07-08T14:30)
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    /**
     * Returns the current system date and time in ISO format (yyyy-MM-dd'T'HH:mm).
     *
     * @return formatted current date-time string
     */
    public static String getCurrentDateTimeInIsoFormat() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(FORMATTER);
    }

    /**
     * Returns the current system date and time plus 30 minutes in ISO format (yyyy-MM-dd'T'HH:mm).
     *
     * @return formatted future date-time string (30 minutes ahead)
     */
    public static String getDateTimePlus30MinutesInIsoFormat() {
        LocalDateTime plus30 = LocalDateTime.now().plusMinutes(30);
        return plus30.format(FORMATTER);
    }
}
