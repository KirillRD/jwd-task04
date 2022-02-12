package com.epam.library.service.exception.validation.book.empty;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when book's publisher is empty
 */
public class EmptyPublisherException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = -8742837129984407493L;

    public EmptyPublisherException() {
        super();
    }

    public EmptyPublisherException(String message) {
        super(message);
    }

    public EmptyPublisherException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPublisherException(Throwable cause) {
        super(cause);
    }
}
