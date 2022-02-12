package com.epam.library.service.exception.validation.review;

import com.epam.library.service.exception.validation.ReviewValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of review's rating is invalid
 */
public class InvalidRatingFormatException extends ReviewValidationException {
    @Serial
    private static final long serialVersionUID = 9221437341832558654L;

    public InvalidRatingFormatException() {
        super();
    }

    public InvalidRatingFormatException(String message) {
        super(message);
    }

    public InvalidRatingFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRatingFormatException(Throwable cause) {
        super(cause);
    }
}
