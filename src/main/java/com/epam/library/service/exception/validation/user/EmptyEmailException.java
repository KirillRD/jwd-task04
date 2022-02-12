package com.epam.library.service.exception.validation.user;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when user's email is empty
 */
public class EmptyEmailException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = 5424709935674140919L;

    public EmptyEmailException() {
        super();
    }

    public EmptyEmailException(String message) {
        super(message);
    }

    public EmptyEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyEmailException(Throwable cause) {
        super(cause);
    }
}
