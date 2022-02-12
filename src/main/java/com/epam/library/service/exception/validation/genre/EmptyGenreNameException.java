package com.epam.library.service.exception.validation.genre;

import com.epam.library.service.exception.validation.GenreValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when genre's name is empty
 */
public class EmptyGenreNameException extends GenreValidationException {
    @Serial
    private static final long serialVersionUID = -5189514767787016701L;

    public EmptyGenreNameException() {
        super();
    }

    public EmptyGenreNameException(String message) {
        super(message);
    }

    public EmptyGenreNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyGenreNameException(Throwable cause) {
        super(cause);
    }
}
