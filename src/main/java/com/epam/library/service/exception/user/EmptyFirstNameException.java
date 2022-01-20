package com.epam.library.service.exception.user;

import com.epam.library.service.exception.UserException;

public class EmptyFirstNameException extends UserException {
    public EmptyFirstNameException() {
        super();
    }

    public EmptyFirstNameException(String message) {
        super(message);
    }

    public EmptyFirstNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyFirstNameException(Throwable cause) {
        super(cause);
    }
}
