package com.epam.library.service.exception.validation.book.empty;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when book's publication year is empty
 */
public class EmptyPublicationYearException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 1624743741416844220L;

    public EmptyPublicationYearException() {
        super();
    }

    public EmptyPublicationYearException(String message) {
        super(message);
    }

    public EmptyPublicationYearException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPublicationYearException(Throwable cause) {
        super(cause);
    }
}
