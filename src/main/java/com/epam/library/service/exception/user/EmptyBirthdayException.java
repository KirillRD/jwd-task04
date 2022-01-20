package com.epam.library.service.exception.user;

import com.epam.library.service.exception.UserException;

public class EmptyBirthdayException extends UserException {
    public EmptyBirthdayException() {
        super();
    }

    public EmptyBirthdayException(String message) {
        super(message);
    }

    public EmptyBirthdayException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyBirthdayException(Throwable cause) {
        super(cause);
    }
}
