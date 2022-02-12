package com.epam.library.service.exception.validation.user;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when user's birthday is empty
 */
public class EmptyBirthdayException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = -3804039901688099368L;

    public EmptyBirthdayException() {
        super();
    }

    public EmptyBirthdayException(String message) {
        super(message);
    }

    public EmptyBirthdayException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyBirthdayException(Throwable cause) {
        super(cause);
    }
}
