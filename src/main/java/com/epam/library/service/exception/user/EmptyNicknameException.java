package com.epam.library.service.exception.user;

import com.epam.library.service.exception.UserException;

public class EmptyNicknameException extends UserException {
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
