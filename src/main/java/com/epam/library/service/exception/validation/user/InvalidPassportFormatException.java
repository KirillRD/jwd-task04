package com.epam.library.service.exception.validation.user;

import com.epam.library.service.exception.validation.UserValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of user's passport is invalid
 */
public class InvalidPassportFormatException extends UserValidationException {
    @Serial
    private static final long serialVersionUID = 7524727392232379042L;

    public InvalidPassportFormatException() {
        super();
    }

    public InvalidPassportFormatException(String message) {
        super(message);
    }

    public InvalidPassportFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPassportFormatException(Throwable cause) {
        super(cause);
    }
}
