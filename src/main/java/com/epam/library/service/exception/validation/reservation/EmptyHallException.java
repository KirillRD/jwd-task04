package com.epam.library.service.exception.validation.reservation;

import com.epam.library.service.exception.validation.ReservationValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when reservation's hall date is empty
 */
public class EmptyHallException extends ReservationValidationException {
    @Serial
    private static final long serialVersionUID = -8088976556581799251L;

    public EmptyHallException() {
        super();
    }

    public EmptyHallException(String message) {
        super(message);
    }

    public EmptyHallException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyHallException(Throwable cause) {
        super(cause);
    }
}
