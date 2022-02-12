package com.epam.library.service.exception.validation.book.empty;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when book's standard number is empty
 */
public class EmptyStandardNumberException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 4103882763805073914L;

    public EmptyStandardNumberException() {
        super();
    }

    public EmptyStandardNumberException(String message) {
        super(message);
    }

    public EmptyStandardNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyStandardNumberException(Throwable cause) {
        super(cause);
    }
}
