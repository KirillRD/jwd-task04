package com.epam.library.service.exception.book.empty;

import com.epam.library.service.exception.BookException;

public class EmptyAuthorException extends BookException {
    public EmptyAuthorException() {
        super();
    }

    public EmptyAuthorException(String message) {
        super(message);
    }

    public EmptyAuthorException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyAuthorException(Throwable cause) {
        super(cause);
    }
}
