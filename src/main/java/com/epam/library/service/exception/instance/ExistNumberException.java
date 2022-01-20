package com.epam.library.service.exception.instance;

import com.epam.library.service.exception.InstanceException;

public class ExistNumberException extends InstanceException {
    public ExistNumberException() {
        super();
    }

    public ExistNumberException(String message) {
        super(message);
    }

    public ExistNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistNumberException(Throwable cause) {
        super(cause);
    }
}
