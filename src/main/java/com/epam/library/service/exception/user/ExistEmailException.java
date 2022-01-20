package com.epam.library.service.exception.user;

import com.epam.library.service.exception.UserException;

public class ExistEmailException extends UserException {
    public ExistEmailException() {
        super();
    }

    public ExistEmailException(String message) {
        super(message);
    }

    public ExistEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistEmailException(Throwable cause) {
        super(cause);
    }
}
