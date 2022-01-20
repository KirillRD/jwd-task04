package com.epam.library.service.exception.user;

import com.epam.library.service.exception.UserException;

public class EmptyGenderException extends UserException {
    public EmptyGenderException() {
        super();
    }

    public EmptyGenderException(String message) {
        super(message);
    }

    public EmptyGenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyGenderException(Throwable cause) {
        super(cause);
    }
}
