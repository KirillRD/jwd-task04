package com.epam.library.service.exception.book;

import com.epam.library.service.exception.BookException;

public class InvalidPartFormatException extends BookException {
    public InvalidPartFormatException() {
        super();
    }

    public InvalidPartFormatException(String message) {
        super(message);
    }

    public InvalidPartFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPartFormatException(Throwable cause) {
        super(cause);
    }
}
