package com.epam.library.service.exception.review;

import com.epam.library.service.exception.ReviewException;

public class EmptyRatingException extends ReviewException {
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
