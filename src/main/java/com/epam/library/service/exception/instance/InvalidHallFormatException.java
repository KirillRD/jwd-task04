package com.epam.library.service.exception.instance;

import com.epam.library.service.exception.InstanceException;

public class InvalidHallFormatException extends InstanceException {
    public InvalidHallFormatException() {
        super();
    }

    public InvalidHallFormatException(String message) {
        super(message);
    }

    public InvalidHallFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidHallFormatException(Throwable cause) {
        super(cause);
    }
}
