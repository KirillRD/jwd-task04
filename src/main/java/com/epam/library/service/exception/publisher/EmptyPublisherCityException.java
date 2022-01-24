package com.epam.library.service.exception.publisher;

import com.epam.library.service.exception.PublisherException;

public class EmptyPublisherCityException extends PublisherException {
    public EmptyPublisherCityException() {
        super();
    }

    public EmptyPublisherCityException(String message) {
        super(message);
    }

    public EmptyPublisherCityException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPublisherCityException(Throwable cause) {
        super(cause);
    }
}
