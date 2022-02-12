package com.epam.library.service.exception.validation;

import com.epam.library.service.exception.ValidationException;

import java.io.Serial;
import java.util.List;

/**
 * Thrown when the publisher's data is not valid
 */
public class PublisherValidationException extends ValidationException {
    @Serial
    private static final long serialVersionUID = -2367873532492051376L;

    public PublisherValidationException() {
        super();
    }

    public PublisherValidationException(String message) {
        super(message);
    }

    public PublisherValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PublisherValidationException(Throwable cause) {
        super(cause);
    }

    public PublisherValidationException(List<? extends ValidationException> exceptions) {
        super(exceptions);
    }
}
