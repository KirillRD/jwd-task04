package com.epam.library.service.exception;

import java.util.List;

public class TypeException extends ServiceException {

    private List<TypeException> exceptions;

    public TypeException() {
        super();
    }

    public TypeException(String message) {
        super(message);
    }

    public TypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeException(Throwable cause) {
        super(cause);
    }

    public TypeException(List<TypeException> exceptions) {
        this.exceptions = exceptions;
    }

    public final List<TypeException> getExceptions() {
        return exceptions;
    }
}
