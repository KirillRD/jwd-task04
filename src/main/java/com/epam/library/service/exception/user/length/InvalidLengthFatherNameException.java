package com.epam.library.service.exception.user.length;

import com.epam.library.service.exception.UserException;

public class InvalidLengthFatherNameException extends UserException {
    public InvalidLengthFatherNameException() {
        super();
    }

    public InvalidLengthFatherNameException(String message) {
        super(message);
    }

    public InvalidLengthFatherNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthFatherNameException(Throwable cause) {
        super(cause);
    }
}
