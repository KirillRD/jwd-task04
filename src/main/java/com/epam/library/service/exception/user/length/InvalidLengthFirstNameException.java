package com.epam.library.service.exception.user.length;

import com.epam.library.service.exception.UserException;

public class InvalidLengthFirstNameException extends UserException {
    public InvalidLengthFirstNameException() {
        super();
    }

    public InvalidLengthFirstNameException(String message) {
        super(message);
    }

    public InvalidLengthFirstNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthFirstNameException(Throwable cause) {
        super(cause);
    }
}
