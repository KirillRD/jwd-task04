package com.epam.library.service.exception.validation.user;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of user's gender is invalid
 */
public class InvalidGenderFormatException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = 4732671735421355297L;

    public InvalidGenderFormatException() {
        super();
    }

    public InvalidGenderFormatException(String message) {
        super(message);
    }

    public InvalidGenderFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidGenderFormatException(Throwable cause) {
        super(cause);
    }
}
