package com.epam.library.service.exception.validation.book;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when book with such standard number exists
 */
public class ExistStandardNumberException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = -1533998247136065670L;

    public ExistStandardNumberException() {
        super();
    }

    public ExistStandardNumberException(String message) {
        super(message);
    }

    public ExistStandardNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistStandardNumberException(Throwable cause) {
        super(cause);
    }
}
