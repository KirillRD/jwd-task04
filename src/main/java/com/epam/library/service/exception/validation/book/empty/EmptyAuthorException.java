package com.epam.library.service.exception.validation.book.empty;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when book's author is empty
 */
public class EmptyAuthorException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = -2758850207508816885L;

    public EmptyAuthorException() {
        super();
    }

    public EmptyAuthorException(String message) {
        super(message);
    }

    public EmptyAuthorException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyAuthorException(Throwable cause) {
        super(cause);
    }
}
