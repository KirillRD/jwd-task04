package com.epam.library.service.exception.validation.user;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of user's email is invalid
 */
public class InvalidEmailFormatException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = 4874724295682930069L;

    public InvalidEmailFormatException() {
        super();
    }

    public InvalidEmailFormatException(String message) {
        super(message);
    }

    public InvalidEmailFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEmailFormatException(Throwable cause) {
        super(cause);
    }
}
