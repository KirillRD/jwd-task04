package com.epam.library.service.exception.instance;

import com.epam.library.service.exception.InstanceException;

public class EmptyNumberException extends InstanceException {
    public EmptyNumberException() {
        super();
    }

    public EmptyNumberException(String message) {
        super(message);
    }

    public EmptyNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyNumberException(Throwable cause) {
        super(cause);
    }
}
