package com.epam.library.service.exception.validation.user.length;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of user's last name is invalid
 */
public class InvalidLengthLastNameException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = -8430232353603893712L;

    public InvalidLengthLastNameException() {
        super();
    }

    public InvalidLengthLastNameException(String message) {
        super(message);
    }

    public InvalidLengthLastNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthLastNameException(Throwable cause) {
        super(cause);
    }
}
