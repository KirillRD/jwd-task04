package com.epam.library.controller.session.exception;

import com.epam.library.service.exception.ServiceException;

public class SessionUserException extends ServiceException {
    public SessionUserException() {
        super();
    }

    public SessionUserException(String message) {
        super(message);
    }

    public SessionUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionUserException(Throwable cause) {
        super(cause);
    }
}
