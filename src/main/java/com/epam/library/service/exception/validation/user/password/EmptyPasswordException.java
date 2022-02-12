package com.epam.library.service.exception.validation.user.password;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when user's password is empty
 */
public class EmptyPasswordException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = 8620587586935970801L;

    public EmptyPasswordException() {
        super();
    }

    public EmptyPasswordException(String message) {
        super(message);
    }

    public EmptyPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPasswordException(Throwable cause) {
        super(cause);
    }
}
