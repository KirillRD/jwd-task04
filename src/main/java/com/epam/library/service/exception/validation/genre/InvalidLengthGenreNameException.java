package com.epam.library.service.exception.validation.genre;

import com.epam.library.service.exception.validation.GenreValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of genre's name is invalid
 */
public class InvalidLengthGenreNameException extends GenreValidationException {
    @Serial
    private static final long serialVersionUID = 7608735454462736292L;

    public InvalidLengthGenreNameException() {
        super();
    }

    public InvalidLengthGenreNameException(String message) {
        super(message);
    }

    public InvalidLengthGenreNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthGenreNameException(Throwable cause) {
        super(cause);
    }
}
