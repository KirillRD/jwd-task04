package com.epam.library.dao.exception;

import java.io.Serial;

/**
 * Thrown when error occurred while processing the data
 */
public class DAOException extends Exception {
    @Serial
    private static final long serialVersionUID = -8217140444978201737L;

    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
