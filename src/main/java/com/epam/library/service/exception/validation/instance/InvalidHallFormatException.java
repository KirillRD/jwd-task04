package com.epam.library.service.exception.validation.instance;

import com.epam.library.service.exception.validation.InstanceValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of instance's hall is invalid
 */
public class InvalidHallFormatException extends InstanceValidationException {
    @Serial
    private static final long serialVersionUID = -1814601182895447611L;

    public InvalidHallFormatException() {
        super();
    }

    public InvalidHallFormatException(String message) {
        super(message);
    }

    public InvalidHallFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidHallFormatException(Throwable cause) {
        super(cause);
    }
}
