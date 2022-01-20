package com.epam.library.service.exception.book.empty;

import com.epam.library.service.exception.BookException;

public class EmptyGenreException extends BookException {
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
