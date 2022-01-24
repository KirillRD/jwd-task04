package com.epam.library.service.exception.author;

import com.epam.library.service.exception.AuthorException;

public class EmptyAuthorFirstNameException extends AuthorException {
    public EmptyAuthorFirstNameException() {
        super();
    }

    public EmptyAuthorFirstNameException(String message) {
        super(message);
    }

    public EmptyAuthorFirstNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyAuthorFirstNameException(Throwable cause) {
        super(cause);
    }
}
