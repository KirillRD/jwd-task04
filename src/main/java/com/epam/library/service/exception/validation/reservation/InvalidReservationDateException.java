package com.epam.library.service.exception.validation.reservation;

import com.epam.library.service.exception.validation.ReservationValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when reservation's date is invalid
 */
public class InvalidReservationDateException extends ReservationValidationException {
    @Serial
    private static final long serialVersionUID = 805624432935566359L;

    public InvalidReservationDateException() {
        super();
    }

    public InvalidReservationDateException(String message) {
        super(message);
    }

    public InvalidReservationDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidReservationDateException(Throwable cause) {
        super(cause);
    }
}
