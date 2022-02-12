package com.epam.library.service.exception.validation.publisher;

import com.epam.library.service.exception.validation.PublisherValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when publisher exists
 */
public class ExistPublisherException extends PublisherValidationException {
    @Serial
    private static final long serialVersionUID = -1542442418495126815L;

    public ExistPublisherException() {
        super();
    }

    public ExistPublisherException(String message) {
        super(message);
    }

    public ExistPublisherException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistPublisherException(Throwable cause) {
        super(cause);
    }
}
