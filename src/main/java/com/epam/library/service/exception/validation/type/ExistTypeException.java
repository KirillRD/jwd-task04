package com.epam.library.service.exception.validation.type;

import com.epam.library.service.exception.validation.TypeValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when type exists
 */
public class ExistTypeException extends TypeValidationException {
    @Serial
    private static final long serialVersionUID = 6817042747679123354L;

    public ExistTypeException() {
        super();
    }

    public ExistTypeException(String message) {
        super(message);
    }

    public ExistTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistTypeException(Throwable cause) {
        super(cause);
    }
}
