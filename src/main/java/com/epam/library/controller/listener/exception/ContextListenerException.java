package com.epam.library.controller.listener.exception;

import java.io.Serial;

public class ContextListenerException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4567760727863773720L;

    public ContextListenerException() {
        super();
    }

    public ContextListenerException(String message) {
        super(message);
    }

    public ContextListenerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContextListenerException(Throwable cause) {
        super(cause);
    }
}
