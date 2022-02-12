package com.epam.library.service.exception.validation.user.length;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of user's first name is invalid
 */
public class InvalidLengthFirstNameException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = -7414398367763042000L;

    public InvalidLengthFirstNameException() {
        super();
    }

    public InvalidLengthFirstNameException(String message) {
        super(message);
    }

    public InvalidLengthFirstNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthFirstNameException(Throwable cause) {
        super(cause);
    }
}
