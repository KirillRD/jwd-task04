package com.epam.library.service.exception.validation.publisher;

import com.epam.library.service.exception.validation.PublisherValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when publisher's city date is empty
 */
public class EmptyPublisherCityException extends PublisherValidationException {
    @Serial
    private static final long serialVersionUID = -7795079331087008470L;

    public EmptyPublisherCityException() {
        super();
    }

    public EmptyPublisherCityException(String message) {
        super(message);
    }

    public EmptyPublisherCityException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPublisherCityException(Throwable cause) {
        super(cause);
    }
}
