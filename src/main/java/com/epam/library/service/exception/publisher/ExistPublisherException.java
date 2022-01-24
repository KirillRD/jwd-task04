package com.epam.library.service.exception.publisher;

import com.epam.library.service.exception.PublisherException;

public class ExistPublisherException extends PublisherException {
    public ExistPublisherException() {
        super();
    }

    public ExistPublisherException(String message) {
        super(message);
    }

    public ExistPublisherException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistPublisherException(Throwable cause) {
        super(cause);
    }
}
