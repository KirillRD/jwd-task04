package com.epam.library.service.exception.validation.user;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of user's birthday is invalid
 */
public class InvalidBirthdayFormatException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = -3111450430793660032L;

    public InvalidBirthdayFormatException() {
        super();
    }

    public InvalidBirthdayFormatException(String message) {
        super(message);
    }

    public InvalidBirthdayFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBirthdayFormatException(Throwable cause) {
        super(cause);
    }
}
