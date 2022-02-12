package com.epam.library.service.exception.validation.user.length;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of user's father name is invalid
 */
public class InvalidLengthFatherNameException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = 8635849452860287556L;

    public InvalidLengthFatherNameException() {
        super();
    }

    public InvalidLengthFatherNameException(String message) {
        super(message);
    }

    public InvalidLengthFatherNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthFatherNameException(Throwable cause) {
        super(cause);
    }
}
