package com.epam.library.service.exception.user.length;

import com.epam.library.service.exception.UserException;

public class InvalidLengthNicknameException extends UserException {
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
