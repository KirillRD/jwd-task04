package com.epam.library.service.exception.book;

import com.epam.library.service.exception.BookException;

public class InvalidPagesFormatException extends BookException {
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
