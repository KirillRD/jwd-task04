package com.epam.library.service.exception.validation.book;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of book's price is invalid
 */
public class InvalidPriceFormatException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 8945459567897973807L;

    public InvalidPriceFormatException() {
        super();
    }

    public InvalidPriceFormatException(String message) {
        super(message);
    }

    public InvalidPriceFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPriceFormatException(Throwable cause) {
        super(cause);
    }
}
