package com.epam.library.service.exception.validation.instance;

import com.epam.library.service.exception.validation.InstanceValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when instance's number is empty
 */
public class EmptyNumberException extends InstanceValidationException {
    @Serial
    private static final long serialVersionUID = -2881715529766238435L;

    public EmptyNumberException() {
        super();
    }

    public EmptyNumberException(String message) {
        super(message);
    }

    public EmptyNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyNumberException(Throwable cause) {
        super(cause);
    }
}
