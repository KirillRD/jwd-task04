package com.epam.library.service.exception.validation.instance;

import com.epam.library.service.exception.validation.InstanceValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when instance with such number exists
 */
public class ExistNumberException extends InstanceValidationException {
    @Serial
    private static final long serialVersionUID = -6612527430463296729L;

    public ExistNumberException() {
        super();
    }

    public ExistNumberException(String message) {
        super(message);
    }

    public ExistNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistNumberException(Throwable cause) {
        super(cause);
    }
}
