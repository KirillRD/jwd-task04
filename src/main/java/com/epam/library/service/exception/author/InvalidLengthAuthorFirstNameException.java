package com.epam.library.service.exception.author;

import com.epam.library.service.exception.AuthorException;

public class InvalidLengthAuthorFirstNameException extends AuthorException {
    public InvalidLengthAuthorFirstNameException() {
        super();
    }

    public InvalidLengthAuthorFirstNameException(String message) {
        super(message);
    }

    public InvalidLengthAuthorFirstNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthAuthorFirstNameException(Throwable cause) {
        super(cause);
    }
}
