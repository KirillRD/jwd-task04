package com.epam.library.service.exception.validation.author;

import com.epam.library.service.exception.validation.AuthorValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of author's last name is invalid
 */
public class InvalidLengthAuthorLastNameException extends AuthorValidationException {
    @Serial
    private static final long serialVersionUID = 1846258630851962066L;

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
