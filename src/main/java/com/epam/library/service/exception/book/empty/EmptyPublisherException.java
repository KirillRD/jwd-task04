package com.epam.library.service.exception.book.empty;

import com.epam.library.service.exception.BookException;

public class EmptyPublisherException extends BookException {
    public EmptyPublisherException() {
        super();
    }

    public EmptyPublisherException(String message) {
        super(message);
    }

    public EmptyPublisherException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPublisherException(Throwable cause) {
        super(cause);
    }
}
