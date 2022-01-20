package com.epam.library.service.exception.user.length;

import com.epam.library.service.exception.UserException;

public class InvalidLengthLastNameException extends UserException {
    public InvalidLengthLastNameException() {
        super();
    }

    public InvalidLengthLastNameException(String message) {
        super(message);
    }

    public InvalidLengthLastNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthLastNameException(Throwable cause) {
        super(cause);
    }
}
