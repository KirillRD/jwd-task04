package com.epam.library.service.exception;

import java.util.List;

public class InstanceException extends ServiceException {

    private List<InstanceException> exceptions;

    public InstanceException() {
        super();
    }

    public InstanceException(String message) {
        super(message);
    }

    public InstanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceException(Throwable cause) {
        super(cause);
    }

    public InstanceException(List<InstanceException> exceptions) {
        this.exceptions = exceptions;
    }

    public final List<InstanceException> getExceptions() {
        return exceptions;
    }
}
