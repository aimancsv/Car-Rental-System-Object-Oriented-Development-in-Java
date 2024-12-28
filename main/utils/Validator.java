package main.utils;

import main.exception.ValidationException;

/**
 * 1. validate if its empty & unexpected input
 */
public class Validator {
    public static void validateNotEmpty(String text, String errorMsg) throws ValidationException {
        if (StringFormat.isBlank(text))
            throw new ValidationException(errorMsg);
    }

    public static void validateMatchRegex(String text, String regex, String errorMsg) throws ValidationException {
        if (text == null || !text.matches(regex))
            throw new ValidationException(errorMsg);
    }

    public static void validateIsInteger(String text, String errorMsg) throws ValidationException {
        if (!StringFormat.isInteger(text))
            throw new ValidationException(errorMsg);
    }

    public static void validateIsCurrency(String text, String errorMsg) throws ValidationException {
        validateMatchRegex(text, "(\\$)?[0-9]+\\.*[0-9]{0,2}", errorMsg);
    }
}
