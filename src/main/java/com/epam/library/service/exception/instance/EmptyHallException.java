package com.epam.library.service.exception.instance;

import com.epam.library.service.exception.InstanceException;

public class EmptyHallException extends InstanceException {
    public EmptyHallException() {
        super();
    }

    public EmptyHallException(String message) {
        super(message);
    }

    public EmptyHallException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyHallException(Throwable cause) {
        super(cause);
    }
}
