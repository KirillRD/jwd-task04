package com.epam.library.service.exception.validation;

import com.epam.library.service.exception.ValidationException;

import java.io.Serial;
import java.util.List;

/**
 * Thrown when the review's data is not valid
 */
public class ReviewValidationException extends ValidationException {
    @Serial
    private static final long serialVersionUID = 3011858587729759679L;

    public ReviewValidationException() {
        super();
    }

    public ReviewValidationException(String message) {
        super(message);
    }

    public ReviewValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReviewValidationException(Throwable cause) {
        super(cause);
    }

    public ReviewValidationException(List<? extends ValidationException> exceptions) {
        super(exceptions);
    }
}
