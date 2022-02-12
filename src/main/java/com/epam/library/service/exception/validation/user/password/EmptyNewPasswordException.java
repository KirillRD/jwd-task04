package com.epam.library.service.exception.validation.user.password;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when user's new password is empty
 */
public class EmptyNewPasswordException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = -7929968628100816329L;

    public EmptyNewPasswordException() {
        super();
    }

    public EmptyNewPasswordException(String message) {
        super(message);
    }

    public EmptyNewPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyNewPasswordException(Throwable cause) {
        super(cause);
    }
}
