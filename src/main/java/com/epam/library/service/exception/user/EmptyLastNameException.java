package com.epam.library.service.exception.user;

import com.epam.library.service.exception.UserException;

public class EmptyLastNameException extends UserException {
    public EmptyLastNameException() {
        super();
    }

    public EmptyLastNameException(String message) {
        super(message);
    }

    public EmptyLastNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyLastNameException(Throwable cause) {
        super(cause);
    }
}
