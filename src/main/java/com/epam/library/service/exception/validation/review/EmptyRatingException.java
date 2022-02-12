package com.epam.library.service.exception.validation.review;

import com.epam.library.service.exception.validation.ReviewValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when review's rating is empty
 */
public class EmptyRatingException extends ReviewValidationException {
    @Serial
    private static final long serialVersionUID = 1503796695688568230L;

    public EmptyRatingException() {
        super();
    }

    public EmptyRatingException(String message) {
        super(message);
    }

    public EmptyRatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyRatingException(Throwable cause) {
        super(cause);
    }
}
