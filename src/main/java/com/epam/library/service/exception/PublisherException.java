package com.epam.library.service.exception;

import java.util.List;

public class PublisherException extends ServiceException {

    private List<PublisherException> exceptions;

    public PublisherException() {
        super();
    }

    public PublisherException(String message) {
        super(message);
    }

    public PublisherException(String message, Throwable cause) {
        super(message, cause);
    }

    public PublisherException(Throwable cause) {
        super(cause);
    }

    public PublisherException(List<PublisherException> exceptions) {
        this.exceptions = exceptions;
    }

    public final List<PublisherException> getExceptions() {
        return exceptions;
    }
}
