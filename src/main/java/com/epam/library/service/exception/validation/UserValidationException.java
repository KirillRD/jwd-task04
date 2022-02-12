package com.epam.library.service.exception.validation;

import com.epam.library.service.exception.ValidationException;

import java.io.Serial;
import java.util.List;

/**
 * Thrown when the user's data is not valid
 */
public class UserValidationException extends ValidationException {
    @Serial
    private static final long serialVersionUID = 4441792296537464875L;

    public UserValidationException() {
        super();
    }

    public UserValidationException(String message) {
        super(message);
    }

    public UserValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserValidationException(Throwable cause) {
        super(cause);
    }

    public UserValidationException(List<? extends ValidationException> exceptions) {
        super(exceptions);
    }
}
