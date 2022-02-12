package com.epam.library.service.exception.validation.genre;

import com.epam.library.service.exception.validation.GenreValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when genre exists
 */
public class ExistGenreException extends GenreValidationException {
    @Serial
    private static final long serialVersionUID = 29325473252702920L;

    public ExistGenreException() {
        super();
    }

    public ExistGenreException(String message) {
        super(message);
    }

    public ExistGenreException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistGenreException(Throwable cause) {
        super(cause);
    }
}
