package com.epam.library.service.exception.author;

import com.epam.library.service.exception.AuthorException;

public class InvalidLengthAuthorFatherNameException extends AuthorException {
    public InvalidLengthAuthorFatherNameException() {
        super();
    }

    public InvalidLengthAuthorFatherNameException(String message) {
        super(message);
    }

    public InvalidLengthAuthorFatherNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthAuthorFatherNameException(Throwable cause) {
        super(cause);
    }
}
