package com.epam.library.service.exception.book;

import com.epam.library.service.exception.BookException;

public class InvalidTypeFormatException extends BookException {
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
