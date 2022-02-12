package com.epam.library.service.exception.validation.author;

import com.epam.library.service.exception.validation.AuthorValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of author's first name is invalid
 */
public class InvalidLengthAuthorFirstNameException extends AuthorValidationException {
    @Serial
    private static final long serialVersionUID = 1190002753304795941L;

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
