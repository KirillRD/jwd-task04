package com.epam.library.service.exception.type;

import com.epam.library.service.exception.TypeException;

public class ExistTypeException extends TypeException {
    public ExistTypeException() {
        super();
    }

    public ExistTypeException(String message) {
        super(message);
    }

    public ExistTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistTypeException(Throwable cause) {
        super(cause);
    }
}
