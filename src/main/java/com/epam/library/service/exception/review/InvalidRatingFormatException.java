package com.epam.library.service.exception.review;

import com.epam.library.service.exception.ReviewException;

public class InvalidRatingFormatException extends ReviewException {
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
