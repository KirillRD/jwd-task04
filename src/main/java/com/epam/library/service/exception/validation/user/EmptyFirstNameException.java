package com.epam.library.service.exception.validation.user;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when user's first name is empty
 */
public class EmptyFirstNameException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = -497174426241419955L;

    public EmptyFirstNameException() {
        super();
    }

    public EmptyFirstNameException(String message) {
        super(message);
    }

    public EmptyFirstNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyFirstNameException(Throwable cause) {
        super(cause);
    }
}
