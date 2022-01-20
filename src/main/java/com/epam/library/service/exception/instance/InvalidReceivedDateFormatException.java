package com.epam.library.service.exception.instance;

import com.epam.library.service.exception.InstanceException;

public class InvalidReceivedDateFormatException extends InstanceException {
    public InvalidReceivedDateFormatException() {
        super();
    }

    public InvalidReceivedDateFormatException(String message) {
        super(message);
    }

    public InvalidReceivedDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidReceivedDateFormatException(Throwable cause) {
        super(cause);
    }
}
