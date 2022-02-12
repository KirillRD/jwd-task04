package com.epam.library.service.exception.validation.author;

import com.epam.library.service.exception.validation.AuthorValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when author's last name is empty
 */
public class EmptyAuthorLastNameException extends AuthorValidationException {
    @Serial
    private static final long serialVersionUID = 9168889623666966044L;

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
