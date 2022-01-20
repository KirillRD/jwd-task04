package com.epam.library.service.exception.book;

import com.epam.library.service.exception.BookException;

public class InvalidPublicationYearFormatException extends BookException {
    public InvalidPublicationYearFormatException() {
        super();
    }

    public InvalidPublicationYearFormatException(String message) {
        super(message);
    }

    public InvalidPublicationYearFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPublicationYearFormatException(Throwable cause) {
        super(cause);
    }
}
