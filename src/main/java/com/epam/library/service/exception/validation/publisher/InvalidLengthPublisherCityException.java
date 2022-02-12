package com.epam.library.service.exception.validation.publisher;

import com.epam.library.service.exception.validation.PublisherValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of publisher's city is invalid
 */
public class InvalidLengthPublisherCityException extends PublisherValidationException {
    @Serial
    private static final long serialVersionUID = -7184000289205420589L;

    public InvalidLengthPublisherCityException() {
        super();
    }

    public InvalidLengthPublisherCityException(String message) {
        super(message);
    }

    public InvalidLengthPublisherCityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthPublisherCityException(Throwable cause) {
        super(cause);
    }
}
