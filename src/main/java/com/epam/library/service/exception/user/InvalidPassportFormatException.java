package com.epam.library.service.exception.user;

import com.epam.library.service.exception.UserException;

public class InvalidPassportFormatException extends UserException {
    public InvalidPassportFormatException() {
        super();
    }

    public InvalidPassportFormatException(String message) {
        super(message);
    }

    public InvalidPassportFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPassportFormatException(Throwable cause) {
        super(cause);
    }
}
