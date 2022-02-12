package com.epam.library.service.exception.validation.instance;

import com.epam.library.service.exception.validation.InstanceValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of instance's number is invalid
 */
public class InvalidLengthNumberException extends InstanceValidationException {
    @Serial
    private static final long serialVersionUID = 7085002090733700822L;

    public InvalidLengthNumberException() {
        super();
    }

    public InvalidLengthNumberException(String message) {
        super(message);
    }

    public InvalidLengthNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthNumberException(Throwable cause) {
        super(cause);
    }
}
