package com.epam.library.service.exception.user.password;

import com.epam.library.service.exception.UserException;

public class NotEqualNewPasswordException extends UserException {
    public NotEqualNewPasswordException() {
        super();
    }

    public NotEqualNewPasswordException(String message) {
        super(message);
    }

    public NotEqualNewPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEqualNewPasswordException(Throwable cause) {
        super(cause);
    }
}
