package com.epam.library.service.exception.user;

import com.epam.library.service.exception.UserException;

public class InvalidPhoneFormatException extends UserException {
    public InvalidPhoneFormatException() {
        super();
    }

    public InvalidPhoneFormatException(String message) {
        super(message);
    }

    public InvalidPhoneFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPhoneFormatException(Throwable cause) {
        super(cause);
    }
}
