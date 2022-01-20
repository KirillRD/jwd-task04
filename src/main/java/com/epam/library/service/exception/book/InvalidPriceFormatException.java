package com.epam.library.service.exception.book;

import com.epam.library.service.exception.BookException;

public class InvalidPriceFormatException extends BookException {
    public InvalidPriceFormatException() {
        super();
    }

    public InvalidPriceFormatException(String message) {
        super(message);
    }

    public InvalidPriceFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPriceFormatException(Throwable cause) {
        super(cause);
    }
}
