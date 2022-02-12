package com.epam.library.service.exception.validation.book;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of book's genre is invalid
 */
public class InvalidGenreFormatException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = -6796574547148043013L;

    public InvalidGenreFormatException() {
        super();
    }

    public InvalidGenreFormatException(String message) {
        super(message);
    }

    public InvalidGenreFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidGenreFormatException(Throwable cause) {
        super(cause);
    }
}
