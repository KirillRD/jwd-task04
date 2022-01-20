package com.epam.library.service.exception;

import java.util.List;

public class BookException extends ServiceException {

    private List<BookException> exceptions;

    public BookException() {
        super();
    }

    public BookException(String message) {
        super(message);
    }

    public BookException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookException(Throwable cause) {
        super(cause);
    }

    public BookException(List<BookException> exceptions) {
        this.exceptions = exceptions;
    }

    public final List<BookException> getExceptions() {
        return exceptions;
    }
}
