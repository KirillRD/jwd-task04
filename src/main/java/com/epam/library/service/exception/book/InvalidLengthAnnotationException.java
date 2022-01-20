package com.epam.library.service.exception.book;

import com.epam.library.service.exception.BookException;

public class InvalidLengthAnnotationException extends BookException {
    public InvalidLengthAnnotationException() {
        super();
    }

    public InvalidLengthAnnotationException(String message) {
        super(message);
    }

    public InvalidLengthAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthAnnotationException(Throwable cause) {
        super(cause);
    }
}
