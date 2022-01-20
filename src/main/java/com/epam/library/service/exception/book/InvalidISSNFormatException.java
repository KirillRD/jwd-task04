package com.epam.library.service.exception.book;

import com.epam.library.service.exception.BookException;

public class InvalidISSNFormatException extends BookException {
    public InvalidISSNFormatException() {
        super();
    }

    public InvalidISSNFormatException(String message) {
        super(message);
    }

    public InvalidISSNFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidISSNFormatException(Throwable cause) {
        super(cause);
    }
}
