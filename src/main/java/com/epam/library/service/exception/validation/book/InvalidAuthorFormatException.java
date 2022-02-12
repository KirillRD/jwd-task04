package com.epam.library.service.exception.validation.book;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of book's author is invalid
 */
public class InvalidAuthorFormatException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 7445481358357656787L;

    public InvalidAuthorFormatException() {
        super();
    }

    public InvalidAuthorFormatException(String message) {
        super(message);
    }

    public InvalidAuthorFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAuthorFormatException(Throwable cause) {
        super(cause);
    }
}
