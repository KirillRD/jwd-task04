package com.epam.library.service.exception.validation.type;

import com.epam.library.service.exception.validation.TypeValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of type's name is invalid
 */
public class InvalidLengthTypeNameException extends TypeValidationException {
    @Serial
    private static final long serialVersionUID = -7598110925401440761L;

    public InvalidLengthTypeNameException() {
        super();
    }

    public InvalidLengthTypeNameException(String message) {
        super(message);
    }

    public InvalidLengthTypeNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthTypeNameException(Throwable cause) {
        super(cause);
    }
}
