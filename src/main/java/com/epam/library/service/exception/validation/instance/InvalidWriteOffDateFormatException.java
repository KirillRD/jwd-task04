package com.epam.library.service.exception.validation.instance;

import com.epam.library.service.exception.validation.InstanceValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of instance's write off date is invalid
 */
public class InvalidWriteOffDateFormatException extends InstanceValidationException {
    @Serial
    private static final long serialVersionUID = 123697936274701297L;

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
