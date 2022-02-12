package com.epam.library.service.exception.validation.user;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of user's phone is invalid
 */
public class InvalidPhoneFormatException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = -1812422038128936282L;

    public InvalidPhoneFormatException() {
        super();
    }

    public InvalidPhoneFormatException(String message) {
        super(message);
    }

    public InvalidPhoneFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPhoneFormatException(Throwable cause) {
        super(cause);
    }
}
