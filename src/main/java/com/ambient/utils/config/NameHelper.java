package com.ambient.utils.config;

import com.github.javafaker.Faker;

/**
 * Utility class for generating fake names and notes for testing purposes.
 *
 * <p>Uses the {@link Faker} library to create realistic-looking data that helps simulate
 * real-world inputs during test execution.</p>
 */
public class NameHelper {

    // Singleton instance of Faker for performance and consistency
    private static final Faker faker = new Faker();

    /**
     * Generates a random full name consisting of a first and last name.
     *
     * @return A full name in the format "First Last".
     */
    public static String generateRandomFullName() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    /**
     * Generates a sample note string using a random sentence.
     *
     * @return A note in the format "test notes - [random sentence]".
     */
    public static String generateNotes() {
        return "test notes - " + faker.lorem().sentence();
    }
}
