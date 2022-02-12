package com.epam.library.service.exception.validation;

import com.epam.library.service.exception.ValidationException;

import java.io.Serial;
import java.util.List;

/**
 * Thrown when the author's data is not valid
 */
public class AuthorValidationException extends ValidationException {
    @Serial
    private static final long serialVersionUID = 8771322550885932873L;

    public AuthorValidationException() {
        super();
    }

    public AuthorValidationException(String message) {
        super(message);
    }

    public AuthorValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorValidationException(Throwable cause) {
        super(cause);
    }

    public AuthorValidationException(List<? extends ValidationException> exceptions) {
        super(exceptions);
    }
}
