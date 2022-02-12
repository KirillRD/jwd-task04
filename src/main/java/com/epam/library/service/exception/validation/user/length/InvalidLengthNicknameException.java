package com.epam.library.service.exception.validation.user.length;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when length of user's nickname is invalid
 */
public class InvalidLengthNicknameException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = -903655265437330810L;

    public InvalidLengthNicknameException() {
        super();
    }

    public InvalidLengthNicknameException(String message) {
        super(message);
    }

    public InvalidLengthNicknameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthNicknameException(Throwable cause) {
        super(cause);
    }
}
