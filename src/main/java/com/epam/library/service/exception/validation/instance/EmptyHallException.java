package com.epam.library.service.exception.validation.instance;

import com.epam.library.service.exception.validation.InstanceValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when instance's hall is empty
 */
public class EmptyHallException extends InstanceValidationException {
    @Serial
    private static final long serialVersionUID = -9164249420668098872L;

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
