package com.epam.library.service.exception.book;

import com.epam.library.service.exception.BookException;

public class InvalidGenreFormatException extends BookException {
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
