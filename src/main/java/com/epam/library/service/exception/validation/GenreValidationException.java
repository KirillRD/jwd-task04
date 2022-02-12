package com.epam.library.service.exception.validation;

import com.epam.library.service.exception.ValidationException;

import java.io.Serial;
import java.util.List;

/**
 * Thrown when the genre's data is not valid
 */
public class GenreValidationException extends ValidationException {
    @Serial
    private static final long serialVersionUID = -616182296374621641L;

    public GenreValidationException() {
        super();
    }

    public GenreValidationException(String message) {
        super(message);
    }

    public GenreValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenreValidationException(Throwable cause) {
        super(cause);
    }

    public GenreValidationException(List<? extends ValidationException> exceptions) {
        super(exceptions);
    }
}
