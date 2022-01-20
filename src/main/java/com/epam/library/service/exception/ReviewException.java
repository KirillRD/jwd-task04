package com.epam.library.service.exception;

import java.util.List;

public class ReviewException extends ServiceException {

    private List<ReviewException> exceptions;

    public ReviewException() {
        super();
    }

    public ReviewException(String message) {
        super(message);
    }

    public ReviewException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReviewException(Throwable cause) {
        super(cause);
    }

    public ReviewException(List<ReviewException> exceptions) {
        this.exceptions = exceptions;
    }

    public final List<ReviewException> getExceptions() {
        return exceptions;
    }
}
