package com.epam.library.service.exception.book;

import com.epam.library.service.exception.BookException;

public class ExistStandardNumberException extends BookException {
    public ExistStandardNumberException() {
        super();
    }

    public ExistStandardNumberException(String message) {
        super(message);
    }

    public ExistStandardNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistStandardNumberException(Throwable cause) {
        super(cause);
    }
}
