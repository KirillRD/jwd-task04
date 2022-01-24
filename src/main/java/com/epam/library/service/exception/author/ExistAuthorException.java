package com.epam.library.service.exception.author;

import com.epam.library.service.exception.AuthorException;

public class ExistAuthorException extends AuthorException {
    public ExistAuthorException() {
        super();
    }

    public ExistAuthorException(String message) {
        super(message);
    }

    public ExistAuthorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistAuthorException(Throwable cause) {
        super(cause);
    }
}
