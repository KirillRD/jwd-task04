package com.epam.library.service.exception.validation.publisher;

import com.epam.library.service.exception.validation.PublisherValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of publisher's name is invalid
 */
public class InvalidLengthPublisherNameException extends PublisherValidationException {
    @Serial
    private static final long serialVersionUID = -2429822308011350245L;

    public InvalidLengthPublisherNameException() {
        super();
    }

    public InvalidLengthPublisherNameException(String message) {
        super(message);
    }

    public InvalidLengthPublisherNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthPublisherNameException(Throwable cause) {
        super(cause);
    }
}
