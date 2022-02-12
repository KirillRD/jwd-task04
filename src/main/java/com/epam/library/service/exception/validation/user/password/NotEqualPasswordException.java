package com.epam.library.service.exception.validation.user.password;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when password and repeated password are not equal
 */
public class NotEqualPasswordException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = 7579470310744989085L;

    public NotEqualPasswordException() {
        super();
    }

    public NotEqualPasswordException(String message) {
        super(message);
    }

    public NotEqualPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEqualPasswordException(Throwable cause) {
        super(cause);
    }
}
