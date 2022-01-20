package com.epam.library.service.exception.instance;

import com.epam.library.service.exception.InstanceException;

public class InvalidLengthNumberException extends InstanceException {
    public InvalidLengthNumberException() {
        super();
    }

    public InvalidLengthNumberException(String message) {
        super(message);
    }

    public InvalidLengthNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthNumberException(Throwable cause) {
        super(cause);
    }
}
