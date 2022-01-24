package com.epam.library.service.exception.genre;

import com.epam.library.service.exception.GenreException;

public class EmptyGenreNameException extends GenreException {
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
