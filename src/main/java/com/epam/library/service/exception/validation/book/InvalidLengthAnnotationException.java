package com.epam.library.service.exception.validation.book;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of book's annotation is invalid
 */
public class InvalidLengthAnnotationException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 1613250129392615514L;

    public InvalidLengthAnnotationException() {
        super();
    }

    public InvalidLengthAnnotationException(String message) {
        super(message);
    }

    public InvalidLengthAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthAnnotationException(Throwable cause) {
        super(cause);
    }
}
