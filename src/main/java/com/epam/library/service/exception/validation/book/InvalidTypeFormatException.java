package com.epam.library.service.exception.validation.book;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of book's type is invalid
 */
public class InvalidTypeFormatException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 7884057132108508652L;

    public InvalidTypeFormatException() {
        super();
    }

    public InvalidTypeFormatException(String message) {
        super(message);
    }

    public InvalidTypeFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTypeFormatException(Throwable cause) {
        super(cause);
    }
}
