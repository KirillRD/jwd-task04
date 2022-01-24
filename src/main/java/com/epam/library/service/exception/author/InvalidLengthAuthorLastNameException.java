package com.epam.library.service.exception.author;

import com.epam.library.service.exception.AuthorException;

public class InvalidLengthAuthorLastNameException extends AuthorException {
    public InvalidLengthAuthorLastNameException() {
        super();
    }

    public InvalidLengthAuthorLastNameException(String message) {
        super(message);
    }

    public InvalidLengthAuthorLastNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthAuthorLastNameException(Throwable cause) {
        super(cause);
    }
}
