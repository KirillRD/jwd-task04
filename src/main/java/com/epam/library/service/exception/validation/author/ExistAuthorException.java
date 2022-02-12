package com.epam.library.service.exception.validation.author;

import com.epam.library.service.exception.validation.AuthorValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when author exists
 */
public class ExistAuthorException extends AuthorValidationException {
    @Serial
    private static final long serialVersionUID = -1157455070225027960L;

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
