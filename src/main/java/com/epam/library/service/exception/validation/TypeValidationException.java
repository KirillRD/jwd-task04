package com.epam.library.service.exception.validation;

import com.epam.library.service.exception.ValidationException;

import java.io.Serial;
import java.util.List;

/**
 * Thrown when the type's data is not valid
 */
public class TypeValidationException extends ValidationException {
    @Serial
    private static final long serialVersionUID = -52597708131008034L;

    public TypeValidationException() {
        super();
    }

    public TypeValidationException(String message) {
        super(message);
    }

    public TypeValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeValidationException(Throwable cause) {
        super(cause);
    }

    public TypeValidationException(List<? extends ValidationException> exceptions) {
        super(exceptions);
    }
}
