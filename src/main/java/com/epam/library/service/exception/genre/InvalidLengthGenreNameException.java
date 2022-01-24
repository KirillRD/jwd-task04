package com.epam.library.service.exception.genre;

import com.epam.library.service.exception.GenreException;

public class InvalidLengthGenreNameException extends GenreException {
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
