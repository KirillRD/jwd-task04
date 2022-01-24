package com.epam.library.service.exception;

import java.util.List;

public class AuthorException extends ServiceException {

    private List<AuthorException> exceptions;

    public AuthorException() {
        super();
    }

    public AuthorException(String message) {
        super(message);
    }

    public AuthorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorException(Throwable cause) {
        super(cause);
    }

    public AuthorException(List<AuthorException> exceptions) {
        this.exceptions = exceptions;
    }

    public final List<AuthorException> getExceptions() {
        return exceptions;
    }
}
