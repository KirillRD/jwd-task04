package com.epam.library.service.exception.book.empty;

import com.epam.library.service.exception.BookException;

public class EmptyStandardNumberException extends BookException {
    public EmptyStandardNumberException() {
        super();
    }

    public EmptyStandardNumberException(String message) {
        super(message);
    }

    public EmptyStandardNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyStandardNumberException(Throwable cause) {
        super(cause);
    }
}
