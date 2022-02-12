package com.epam.library.service.exception.validation.book;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of book's pages is invalid
 */
public class InvalidPagesFormatException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 1632890826540720881L;

    public InvalidPagesFormatException() {
        super();
    }

    public InvalidPagesFormatException(String message) {
        super(message);
    }

    public InvalidPagesFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPagesFormatException(Throwable cause) {
        super(cause);
    }
}
