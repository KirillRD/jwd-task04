package com.epam.library.service.exception.publisher;

import com.epam.library.service.exception.PublisherException;

public class InvalidLengthPublisherNameException extends PublisherException {
    public InvalidLengthPublisherNameException() {
        super();
    }

    public InvalidLengthPublisherNameException(String message) {
        super(message);
    }

    public InvalidLengthPublisherNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthPublisherNameException(Throwable cause) {
        super(cause);
    }
}
