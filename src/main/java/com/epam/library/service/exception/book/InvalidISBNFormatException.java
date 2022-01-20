package com.epam.library.service.exception.book;

import com.epam.library.service.exception.BookException;

public class InvalidISBNFormatException extends BookException {
    public InvalidISBNFormatException() {
        super();
    }

    public InvalidISBNFormatException(String message) {
        super(message);
    }

    public InvalidISBNFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidISBNFormatException(Throwable cause) {
        super(cause);
    }
}
