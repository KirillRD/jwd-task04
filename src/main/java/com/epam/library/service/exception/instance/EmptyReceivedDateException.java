package com.epam.library.service.exception.instance;

import com.epam.library.service.exception.InstanceException;

public class EmptyReceivedDateException extends InstanceException {
    public EmptyReceivedDateException() {
        super();
    }

    public EmptyReceivedDateException(String message) {
        super(message);
    }

    public EmptyReceivedDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyReceivedDateException(Throwable cause) {
        super(cause);
    }
}
