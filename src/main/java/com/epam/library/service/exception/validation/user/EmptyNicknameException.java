package com.epam.library.service.exception.validation.user;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when user's nickname is empty
 */
public class EmptyNicknameException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = 1663372240590090503L;

    public EmptyNicknameException() {
        super();
    }

    public EmptyNicknameException(String message) {
        super(message);
    }

    public EmptyNicknameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyNicknameException(Throwable cause) {
        super(cause);
    }
}
