package com.epam.library.service.exception.review;

import com.epam.library.service.exception.ReviewException;

public class InvalidLengthCommentException extends ReviewException {
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
