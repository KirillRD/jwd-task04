package com.epam.library.service.exception.user.password;

import com.epam.library.service.exception.UserException;

public class EmptyNewPasswordException extends UserException {
    public EmptyNewPasswordException() {
        super();
    }

    public EmptyNewPasswordException(String message) {
        super(message);
    }

    public EmptyNewPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyNewPasswordException(Throwable cause) {
        super(cause);
    }
}
