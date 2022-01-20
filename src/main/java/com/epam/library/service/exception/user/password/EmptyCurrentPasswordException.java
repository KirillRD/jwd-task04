package com.epam.library.service.exception.user.password;

import com.epam.library.service.exception.UserException;

public class EmptyCurrentPasswordException extends UserException {
    public EmptyCurrentPasswordException() {
        super();
    }

    public EmptyCurrentPasswordException(String message) {
        super(message);
    }

    public EmptyCurrentPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyCurrentPasswordException(Throwable cause) {
        super(cause);
    }
}
