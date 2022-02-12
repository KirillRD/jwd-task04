package com.epam.library.service.exception.validation.user;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when entered password and current password are not equal
 */
public class InvalidCurrentPasswordException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = -6993376541219586425L;

    public InvalidCurrentPasswordException() {
        super();
    }

    public InvalidCurrentPasswordException(String message) {
        super(message);
    }

    public InvalidCurrentPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCurrentPasswordException(Throwable cause) {
        super(cause);
    }
}
