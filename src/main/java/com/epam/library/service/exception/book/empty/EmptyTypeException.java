package com.epam.library.service.exception.book.empty;

import com.epam.library.service.exception.BookException;

public class EmptyTypeException extends BookException {
    public EmptyTypeException() {
        super();
    }

    public EmptyTypeException(String message) {
        super(message);
    }

    public EmptyTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyTypeException(Throwable cause) {
        super(cause);
    }
}
