package com.epam.library.service.exception.validation.book.empty;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when book's type is empty
 */
public class EmptyTypeException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 1328400078533055287L;

    public EmptyTypeException() {
        super();
    }

    public EmptyTypeException(String message) {
        super(message);
    }

    public EmptyTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyTypeException(Throwable cause) {
        super(cause);
    }
}
