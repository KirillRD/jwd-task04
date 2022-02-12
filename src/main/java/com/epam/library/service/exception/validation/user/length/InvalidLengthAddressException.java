package com.epam.library.service.exception.validation.user.length;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of user's address is invalid
 */
public class InvalidLengthAddressException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = -3261489113797512705L;

    public InvalidLengthAddressException() {
        super();
    }

    public InvalidLengthAddressException(String message) {
        super(message);
    }

    public InvalidLengthAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthAddressException(Throwable cause) {
        super(cause);
    }
}
