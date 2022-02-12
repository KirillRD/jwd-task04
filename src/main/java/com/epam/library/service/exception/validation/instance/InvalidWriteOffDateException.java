package com.epam.library.service.exception.validation.instance;

import com.epam.library.service.exception.validation.InstanceValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when instance's write off date is invalid
 */
public class InvalidWriteOffDateException extends InstanceValidationException {
    @Serial
    private static final long serialVersionUID = 5945021602884498646L;

    public InvalidWriteOffDateException() {
        super();
    }

    public InvalidWriteOffDateException(String message) {
        super(message);
    }

    public InvalidWriteOffDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidWriteOffDateException(Throwable cause) {
        super(cause);
    }
}
