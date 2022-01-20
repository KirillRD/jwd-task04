package com.epam.library.service.exception.user.password;

import com.epam.library.service.exception.UserException;

public class EmptyRepeatedNewPasswordException extends UserException {
    public EmptyRepeatedNewPasswordException() {
        super();
    }

    public EmptyRepeatedNewPasswordException(String message) {
        super(message);
    }

    public EmptyRepeatedNewPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyRepeatedNewPasswordException(Throwable cause) {
        super(cause);
    }
}
