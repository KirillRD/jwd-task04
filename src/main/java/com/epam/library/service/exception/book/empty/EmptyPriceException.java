package com.epam.library.service.exception.book.empty;

import com.epam.library.service.exception.BookException;

public class EmptyPriceException extends BookException {
    public EmptyPriceException() {
        super();
    }

    public EmptyPriceException(String message) {
        super(message);
    }

    public EmptyPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPriceException(Throwable cause) {
        super(cause);
    }
}
