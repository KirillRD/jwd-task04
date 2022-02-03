package com.epam.library.service.exception.instance;

import com.epam.library.service.exception.InstanceException;

public class InvalidWriteOffDateException extends InstanceException {
    public InvalidWriteOffDateException() {
        super();
    }

    public InvalidWriteOffDateException(String message) {
        super(message);
    }

    public InvalidWriteOffDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidWriteOffDateException(Throwable cause) {
        super(cause);
    }
}
