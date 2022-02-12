package com.epam.library.service.exception.validation.user;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when user with such email exists
 */
public class ExistEmailException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = 8143825992395750094L;

    public ExistEmailException() {
        super();
    }

    public ExistEmailException(String message) {
        super(message);
    }

    public ExistEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistEmailException(Throwable cause) {
        super(cause);
    }
}
