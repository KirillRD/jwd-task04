package com.epam.library.service.exception.user;

import com.epam.library.service.exception.UserException;

public class EmptyEmailException extends UserException {
    public EmptyEmailException() {
        super();
    }

    public EmptyEmailException(String message) {
        super(message);
    }

    public EmptyEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyEmailException(Throwable cause) {
        super(cause);
    }
}
