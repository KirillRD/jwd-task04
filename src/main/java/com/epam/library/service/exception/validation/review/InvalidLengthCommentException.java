package com.epam.library.service.exception.validation.review;

import com.epam.library.service.exception.validation.ReviewValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of review's comment is invalid
 */
public class InvalidLengthCommentException extends ReviewValidationException {
    @Serial
    private static final long serialVersionUID = 1514736200507715336L;

    public InvalidLengthCommentException() {
        super();
    }

    public InvalidLengthCommentException(String message) {
        super(message);
    }

    public InvalidLengthCommentException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthCommentException(Throwable cause) {
        super(cause);
    }
}
