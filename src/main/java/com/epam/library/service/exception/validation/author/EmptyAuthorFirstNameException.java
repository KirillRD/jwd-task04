package com.epam.library.service.exception.validation.author;

import com.epam.library.service.exception.validation.AuthorValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when author's first name is empty
 */
public class EmptyAuthorFirstNameException extends AuthorValidationException {
    @Serial
    private static final long serialVersionUID = -6051590822101182437L;

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
