package com.epam.library.service.exception;

import java.util.List;

public class UserException extends ServiceException {

    private List<UserException> exceptions;

    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    public UserException(List<UserException> exceptions) {
        this.exceptions = exceptions;
    }

    public final List<UserException> getExceptions() {
        return exceptions;
    }
}
