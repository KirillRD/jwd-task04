package com.epam.library.service.exception.type;

import com.epam.library.service.exception.TypeException;

public class InvalidLengthTypeNameException extends TypeException {
    public InvalidLengthTypeNameException() {
        super();
    }

    public InvalidLengthTypeNameException(String message) {
        super(message);
    }

    public InvalidLengthTypeNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLengthTypeNameException(Throwable cause) {
        super(cause);
    }
}
