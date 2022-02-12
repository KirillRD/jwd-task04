package com.epam.library.service.exception.validation.author;

import com.epam.library.service.exception.validation.AuthorValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of author's father name is invalid
 */
public class InvalidLengthAuthorFatherNameException extends AuthorValidationException {
    @Serial
    private static final long serialVersionUID = -5220827361109898041L;

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
