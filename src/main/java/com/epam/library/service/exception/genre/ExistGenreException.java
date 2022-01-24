package com.epam.library.service.exception.genre;

import com.epam.library.service.exception.GenreException;

public class ExistGenreException extends GenreException {
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
