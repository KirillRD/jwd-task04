package com.epam.library.service.exception;

import java.io.Serial;
import java.util.List;

/**
 * Basic validation exception
 */
public class ValidationException extends ServiceException {
    @Serial
    private static final long serialVersionUID = 4992326761886573458L;

    private List<? extends ValidationException> exceptions;

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(List<? extends ValidationException> exceptions) {
        this.exceptions = exceptions;
    }

    public List<? extends ValidationException> getExceptions() {
        return exceptions;
    }
}
