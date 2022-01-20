package com.epam.library.service.exception.user;

import com.epam.library.service.exception.UserException;

public class InvalidGenderFormatException extends UserException {
    public InvalidGenderFormatException() {
        super();
    }

    public InvalidGenderFormatException(String message) {
        super(message);
    }

    public InvalidGenderFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidGenderFormatException(Throwable cause) {
        super(cause);
    }
}
