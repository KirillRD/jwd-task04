package com.epam.library.service.exception.validation.type;

import com.epam.library.service.exception.validation.TypeValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when type's name is empty
 */
public class EmptyTypeNameException extends TypeValidationException {
    @Serial
    private static final long serialVersionUID = 1317746455205556664L;

    public EmptyTypeNameException() {
        super();
    }

    public EmptyTypeNameException(String message) {
        super(message);
    }

    public EmptyTypeNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyTypeNameException(Throwable cause) {
        super(cause);
    }
}
