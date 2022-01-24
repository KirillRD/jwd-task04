package com.epam.library.service.exception.type;

import com.epam.library.service.exception.TypeException;

public class EmptyTypeNameException extends TypeException {
    public EmptyTypeNameException() {
        super();
    }

    public EmptyTypeNameException(String message) {
        super(message);
    }

    public EmptyTypeNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyTypeNameException(Throwable cause) {
        super(cause);
    }
}
