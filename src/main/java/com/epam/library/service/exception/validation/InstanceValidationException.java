package com.epam.library.service.exception.validation;

import com.epam.library.service.exception.ValidationException;

import java.io.Serial;
import java.util.List;

/**
 * Thrown when the instance's data is not valid
 */
public class InstanceValidationException extends ValidationException {
    @Serial
    private static final long serialVersionUID = -8340386110170561061L;

    public InstanceValidationException() {
        super();
    }

    public InstanceValidationException(String message) {
        super(message);
    }

    public InstanceValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceValidationException(Throwable cause) {
        super(cause);
    }

    public InstanceValidationException(List<? extends ValidationException> exceptions) {
        super(exceptions);
    }
}
