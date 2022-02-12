package com.epam.library.service.exception;

import java.io.Serial;

/**
 * Thrown when error occurred while processing the data
 */
public class GeneralException extends ServiceException {
    @Serial
    private static final long serialVersionUID = 4098481111745349953L;

    public GeneralException() {
        super();
    }

    public GeneralException(String message) {
        super(message);
    }

    public GeneralException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralException(Throwable cause) {
        super(cause);
    }
}
