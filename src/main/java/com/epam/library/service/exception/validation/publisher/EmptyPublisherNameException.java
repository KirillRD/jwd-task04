package com.epam.library.service.exception.validation.publisher;

import com.epam.library.service.exception.validation.PublisherValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when publisher's name date is empty
 */
public class EmptyPublisherNameException extends PublisherValidationException {
    @Serial
    private static final long serialVersionUID = -4943368559157512562L;

    public EmptyPublisherNameException() {
        super();
    }

    public EmptyPublisherNameException(String message) {
        super(message);
    }

    public EmptyPublisherNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPublisherNameException(Throwable cause) {
        super(cause);
    }
}
