package com.epam.library.service.exception.book.empty;

import com.epam.library.service.exception.BookException;

public class EmptyPagesException extends BookException {
    public EmptyPagesException() {
        super();
    }

    public EmptyPagesException(String message) {
        super(message);
    }

    public EmptyPagesException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPagesException(Throwable cause) {
        super(cause);
    }
}
