package com.epam.library.service.exception.user;

import com.epam.library.service.exception.UserException;

public class InvalidCurrentPasswordException extends UserException {
    public InvalidCurrentPasswordException() {
        super();
    }

    public InvalidCurrentPasswordException(String message) {
        super(message);
    }

    public InvalidCurrentPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCurrentPasswordException(Throwable cause) {
        super(cause);
    }
}
