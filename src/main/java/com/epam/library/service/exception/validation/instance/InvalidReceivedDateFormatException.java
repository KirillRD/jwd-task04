package com.epam.library.service.exception.validation.instance;

import com.epam.library.service.exception.validation.InstanceValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of instance's received date is invalid
 */
public class InvalidReceivedDateFormatException extends InstanceValidationException {
    @Serial
    private static final long serialVersionUID = 685909982026871432L;

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
