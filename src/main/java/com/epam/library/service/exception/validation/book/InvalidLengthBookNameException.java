package com.epam.library.service.exception.validation.book;

import com.epam.library.service.exception.validation.BookValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of book's name is invalid
 */
public class InvalidLengthBookNameException extends BookValidationException {
    @Serial
    private static final long serialVersionUID = 2267828781361894950L;

    public InvalidLengthBookNameException() {
        super();
    }

    public InvalidLengthBookNameException(String message) {
        super(message);
    }

    public InvalidLengthBookNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthBookNameException(Throwable cause) {
        super(cause);
    }
}
