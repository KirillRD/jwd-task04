package com.epam.library.service.exception.validation.user.password;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when new password and repeated new password are not equal
 */
public class NotEqualNewPasswordException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = 8255993845778091875L;

    public NotEqualNewPasswordException() {
        super();
    }

    public NotEqualNewPasswordException(String message) {
        super(message);
    }

    public NotEqualNewPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEqualNewPasswordException(Throwable cause) {
        super(cause);
    }
}
