package net.javaguides.ems.utils;

import net.javaguides.ems.models.exceptions.BadRequestException;

public class ValidationUtil {

    private ValidationUtil() {
        // Private constructor to prevent instantiation
    }

    public static void validatePositiveNumber(String fieldName, Double value) {
        if (value == null || value <= 0) {
            throw new BadRequestException(fieldName + " must be greater than 0");
        }
    }

    public static void validateNonEmptyString(String fieldName, String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new BadRequestException(fieldName + " cannot be blank or null");
        }
    }

}
