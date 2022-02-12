package com.epam.library.service.exception.validation;

import com.epam.library.service.exception.ValidationException;

import java.io.Serial;
import java.util.List;

/**
 * Thrown when the book's data is not valid
 */
public class BookValidationException extends ValidationException {
    @Serial
    private static final long serialVersionUID = -4945721780703085959L;

    public BookValidationException() {
        super();
    }

    public BookValidationException(String message) {
        super(message);
    }

    public BookValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookValidationException(Throwable cause) {
        super(cause);
    }

    public BookValidationException(List<? extends ValidationException> exceptions) {
        super(exceptions);
    }
}
