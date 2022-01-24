package com.epam.library.service.exception.author;

import com.epam.library.service.exception.AuthorException;

public class EmptyAuthorLastNameException extends AuthorException {
    public EmptyAuthorLastNameException() {
        super();
    }

    public EmptyAuthorLastNameException(String message) {
        super(message);
    }

    public EmptyAuthorLastNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyAuthorLastNameException(Throwable cause) {
        super(cause);
    }
}
