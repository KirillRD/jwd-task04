package com.epam.library.dao.connection_pool.exception;

import java.io.Serial;

public class ConnectionPoolException extends Exception {
    @Serial
    private static final long serialVersionUID = 6497184930357261454L;

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
