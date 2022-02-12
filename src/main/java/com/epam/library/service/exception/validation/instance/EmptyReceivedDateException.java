package com.epam.library.service.exception.validation.instance;

import com.epam.library.service.exception.validation.InstanceValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when instance's received date is empty
 */
public class EmptyReceivedDateException extends InstanceValidationException {
    @Serial
    private static final long serialVersionUID = 2066929689266677521L;

    public EmptyReceivedDateException() {
        super();
    }

    public EmptyReceivedDateException(String message) {
        super(message);
    }

    public EmptyReceivedDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyReceivedDateException(Throwable cause) {
        super(cause);
    }
}
