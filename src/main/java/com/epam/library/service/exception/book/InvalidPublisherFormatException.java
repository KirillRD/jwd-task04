package com.epam.library.service.exception.book;

import com.epam.library.service.exception.BookException;

public class InvalidPublisherFormatException extends BookException {
    public InvalidPublisherFormatException() {
        super();
    }

    public InvalidPublisherFormatException(String message) {
        super(message);
    }

    public InvalidPublisherFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPublisherFormatException(Throwable cause) {
        super(cause);
    }
}
