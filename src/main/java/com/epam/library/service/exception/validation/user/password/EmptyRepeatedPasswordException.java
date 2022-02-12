package com.epam.library.service.exception.validation.user.password;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when user's repeated password is empty
 */
public class EmptyRepeatedPasswordException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = 7509210700235037726L;

    public EmptyRepeatedPasswordException() {
        super();
    }

    public EmptyRepeatedPasswordException(String message) {
        super(message);
    }

    public EmptyRepeatedPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyRepeatedPasswordException(Throwable cause) {
        super(cause);
    }
}
