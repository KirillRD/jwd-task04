package com.epam.library.service.exception.validation.book.empty;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when book's pages is empty
 */
public class EmptyPagesException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 6777855722358782069L;

    public EmptyPagesException() {
        super();
    }

    public EmptyPagesException(String message) {
        super(message);
    }

    public EmptyPagesException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPagesException(Throwable cause) {
        super(cause);
    }
}
