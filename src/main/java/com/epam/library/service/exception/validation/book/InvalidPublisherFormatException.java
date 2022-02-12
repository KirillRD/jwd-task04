package com.epam.library.service.exception.validation.book;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of book's publisher is invalid
 */
public class InvalidPublisherFormatException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = -5207727480825426556L;

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
