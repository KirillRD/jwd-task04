package com.epam.library.service.exception.user;

import com.epam.library.service.exception.UserException;

public class InvalidBirthdayException extends UserException {
    public InvalidBirthdayException() {
        super();
    }

    public InvalidBirthdayException(String message) {
        super(message);
    }

    public InvalidBirthdayException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBirthdayException(Throwable cause) {
        super(cause);
    }
}
