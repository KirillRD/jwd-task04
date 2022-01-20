package com.epam.library.service.exception.user;

import com.epam.library.service.exception.UserException;

public class InvalidBirthdayFormatException extends UserException {
    public InvalidBirthdayFormatException() {
        super();
    }

    public InvalidBirthdayFormatException(String message) {
        super(message);
    }

    public InvalidBirthdayFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBirthdayFormatException(Throwable cause) {
        super(cause);
    }
}
