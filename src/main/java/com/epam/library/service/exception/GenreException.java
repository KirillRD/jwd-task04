package com.epam.library.service.exception;

import java.util.List;

public class GenreException extends ServiceException {

    private List<GenreException> exceptions;

    public GenreException() {
        super();
    }

    public GenreException(String message) {
        super(message);
    }

    public GenreException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenreException(Throwable cause) {
        super(cause);
    }

    public GenreException(List<GenreException> exceptions) {
        this.exceptions = exceptions;
    }

    public final List<GenreException> getExceptions() {
        return exceptions;
    }
}
