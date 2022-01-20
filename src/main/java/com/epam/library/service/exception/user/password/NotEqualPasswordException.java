package com.epam.library.service.exception.user.password;

import com.epam.library.service.exception.UserException;

public class NotEqualPasswordException extends UserException {
    public NotEqualPasswordException() {
        super();
    }

    public NotEqualPasswordException(String message) {
        super(message);
    }

    public NotEqualPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEqualPasswordException(Throwable cause) {
        super(cause);
    }
}
