package com.epam.library.service.exception.validation.book.empty;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when book's genre is empty
 */
public class EmptyGenreException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = -1350552777047020751L;

    public EmptyGenreException() {
        super();
    }

    public EmptyGenreException(String message) {
        super(message);
    }

    public EmptyGenreException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyGenreException(Throwable cause) {
        super(cause);
    }
}
