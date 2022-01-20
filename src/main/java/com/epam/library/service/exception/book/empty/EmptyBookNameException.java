package com.epam.library.service.exception.book.empty;

import com.epam.library.service.exception.BookException;

public class EmptyBookNameException extends BookException {
    public EmptyBookNameException() {
        super();
    }

    public EmptyBookNameException(String message) {
        super(message);
    }

    public EmptyBookNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyBookNameException(Throwable cause) {
        super(cause);
    }
}
