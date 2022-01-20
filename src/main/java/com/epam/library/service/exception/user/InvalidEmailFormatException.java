package com.epam.library.service.exception.user;

import com.epam.library.service.exception.UserException;

public class InvalidEmailFormatException extends UserException {
    public InvalidEmailFormatException() {
        super();
    }

    public InvalidEmailFormatException(String message) {
        super(message);
    }

    public InvalidEmailFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEmailFormatException(Throwable cause) {
        super(cause);
    }
}
