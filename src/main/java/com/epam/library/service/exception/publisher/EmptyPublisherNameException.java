package com.epam.library.service.exception.publisher;

import com.epam.library.service.exception.PublisherException;

public class EmptyPublisherNameException extends PublisherException {
    public EmptyPublisherNameException() {
        super();
    }

    public EmptyPublisherNameException(String message) {
        super(message);
    }

    public EmptyPublisherNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPublisherNameException(Throwable cause) {
        super(cause);
    }
}
