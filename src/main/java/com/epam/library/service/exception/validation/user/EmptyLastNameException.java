package com.epam.library.service.exception.validation.user;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when user's last name is empty
 */
public class EmptyLastNameException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = 8584906274589803628L;

    public EmptyLastNameException() {
        super();
    }

    public EmptyLastNameException(String message) {
        super(message);
    }

    public EmptyLastNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyLastNameException(Throwable cause) {
        super(cause);
    }
}
