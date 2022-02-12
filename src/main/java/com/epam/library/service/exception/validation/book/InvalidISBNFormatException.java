package com.epam.library.service.exception.validation.book;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of book's ISBN is invalid
 */
public class InvalidISBNFormatException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = -9081083588247149813L;

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
