package com.epam.library.service.exception.user.length;

import com.epam.library.service.exception.UserException;

public class InvalidLengthAddressException extends UserException {
    public InvalidLengthAddressException() {
        super();
    }

    public InvalidLengthAddressException(String message) {
        super(message);
    }

    public InvalidLengthAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthAddressException(Throwable cause) {
        super(cause);
    }
}
