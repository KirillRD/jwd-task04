package com.epam.library.service.exception.publisher;

import com.epam.library.service.exception.PublisherException;

public class InvalidLengthPublisherCityException extends PublisherException {
    public InvalidLengthPublisherCityException() {
        super();
    }

    public InvalidLengthPublisherCityException(String message) {
        super(message);
    }

    public InvalidLengthPublisherCityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthPublisherCityException(Throwable cause) {
        super(cause);
    }
}
