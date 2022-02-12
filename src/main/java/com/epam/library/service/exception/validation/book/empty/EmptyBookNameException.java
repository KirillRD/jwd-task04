package com.epam.library.service.exception.validation.book.empty;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when book's name is empty
 */
public class EmptyBookNameException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 5681904123452457258L;

    public EmptyBookNameException() {
        super();
    }

    public EmptyBookNameException(String message) {
        super(message);
    }

    public EmptyBookNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyBookNameException(Throwable cause) {
        super(cause);
    }
}
