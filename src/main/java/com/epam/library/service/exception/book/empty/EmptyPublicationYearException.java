package com.epam.library.service.exception.book.empty;

import com.epam.library.service.exception.BookException;

public class EmptyPublicationYearException extends BookException {
    public EmptyPublicationYearException() {
        super();
    }

    public EmptyPublicationYearException(String message) {
        super(message);
    }

    public EmptyPublicationYearException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPublicationYearException(Throwable cause) {
        super(cause);
    }
}
