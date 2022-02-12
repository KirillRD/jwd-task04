package com.epam.library.service.exception.validation.book;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of book's part is invalid
 */
public class InvalidPartFormatException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 3794156870659146260L;

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
