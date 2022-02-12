package com.epam.library.service.exception.validation.user;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when user's birthday is invalid
 */
public class InvalidBirthdayException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = -3552383217802603540L;

    public InvalidBirthdayException() {
        super();
    }

    public InvalidBirthdayException(String message) {
        super(message);
    }

    public InvalidBirthdayException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBirthdayException(Throwable cause) {
        super(cause);
    }
}
