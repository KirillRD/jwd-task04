package com.epam.library.service.exception.validation.user.password;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when user's current password is empty
 */
public class EmptyCurrentPasswordException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = -6399399207815578875L;

    public EmptyCurrentPasswordException() {
        super();
    }

    public EmptyCurrentPasswordException(String message) {
        super(message);
    }

    public EmptyCurrentPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyCurrentPasswordException(Throwable cause) {
        super(cause);
    }
}
