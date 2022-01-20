package com.epam.library.service.exception.book;

import com.epam.library.service.exception.BookException;

public class InvalidAuthorFormatException extends BookException {
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
