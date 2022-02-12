package com.epam.library.service.exception.validation.user;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when user's gender is empty
 */
public class EmptyGenderException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = -8114288666420082752L;

    public EmptyGenderException() {
        super();
    }

    public EmptyGenderException(String message) {
        super(message);
    }

    public EmptyGenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyGenderException(Throwable cause) {
        super(cause);
    }
}
