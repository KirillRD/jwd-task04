package com.epam.library.service.exception.instance;

import com.epam.library.service.exception.InstanceException;

public class InvalidWriteOffDateFormatException extends InstanceException {
    public InvalidWriteOffDateFormatException() {
        super();
    }

    public InvalidWriteOffDateFormatException(String message) {
        super(message);
    }

    public InvalidWriteOffDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidWriteOffDateFormatException(Throwable cause) {
        super(cause);
    }
}
