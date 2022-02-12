package com.epam.library.service.exception.validation.book;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of book's ISSN is invalid
 */
public class InvalidISSNFormatException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 6053647500487403389L;

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
