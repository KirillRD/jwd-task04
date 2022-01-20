package com.epam.library.service.exception.user.password;

import com.epam.library.service.exception.UserException;

public class EmptyPasswordException extends UserException {
    public EmptyPasswordException() {
        super();
    }

    public EmptyPasswordException(String message) {
        super(message);
    }

    public EmptyPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPasswordException(Throwable cause) {
        super(cause);
    }
}
