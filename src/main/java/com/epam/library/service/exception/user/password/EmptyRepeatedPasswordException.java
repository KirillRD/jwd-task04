package com.epam.library.service.exception.user.password;

import com.epam.library.service.exception.UserException;

public class EmptyRepeatedPasswordException extends UserException {
    public EmptyRepeatedPasswordException() {
        super();
    }

    public EmptyRepeatedPasswordException(String message) {
        super(message);
    }

    public EmptyRepeatedPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyRepeatedPasswordException(Throwable cause) {
        super(cause);
    }
}
