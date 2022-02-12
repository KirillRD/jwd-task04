package com.epam.library.service.exception.validation.book;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of book's publication year is invalid
 */
public class InvalidPublicationYearFormatException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 7260411445706394901L;

    public InvalidPublicationYearFormatException() {
        super();
    }

    public InvalidPublicationYearFormatException(String message) {
        super(message);
    }

    public InvalidPublicationYearFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPublicationYearFormatException(Throwable cause) {
        super(cause);
    }
}
