package com.epam.library.service.exception.validation.user.password;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when user's repeated new password is empty
 */
public class EmptyRepeatedNewPasswordException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = 7274293328236162631L;

    public EmptyRepeatedNewPasswordException() {
        super();
    }

    public EmptyRepeatedNewPasswordException(String message) {
        super(message);
    }

    public EmptyRepeatedNewPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyRepeatedNewPasswordException(Throwable cause) {
        super(cause);
    }
}
