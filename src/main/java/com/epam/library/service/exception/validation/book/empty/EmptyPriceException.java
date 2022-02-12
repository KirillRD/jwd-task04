package com.epam.library.service.exception.validation.book.empty;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when book's price is empty
 */
public class EmptyPriceException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 5995537007683593126L;

    public EmptyPriceException() {
        super();
    }

    public EmptyPriceException(String message) {
        super(message);
    }

    public EmptyPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPriceException(Throwable cause) {
        super(cause);
    }
}
