package com.epam.library.service.exception.book;

import com.epam.library.service.exception.BookException;

public class InvalidLengthBookNameException extends BookException {
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
