package com.epam.library.service.exception;

import java.util.List;

public class ReservationException extends ServiceException {

    private List<ReservationException> exceptions;

    public ReservationException() {
        super();
    }

    public ReservationException(String message) {
        super(message);
    }

    public ReservationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationException(Throwable cause) {
        super(cause);
    }

    public ReservationException(List<ReservationException> exceptions) {
        this.exceptions = exceptions;
    }

    public final List<ReservationException> getExceptions() {
        return exceptions;
    }
}
