package com.epam.library.service.exception.validation.reservation;

import com.epam.library.service.exception.validation.ReservationValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when reservation's date is empty
 */
public class EmptyReservationDateException extends ReservationValidationException {
    @Serial
    private static final long serialVersionUID = 3577614974166225093L;

    public EmptyReservationDateException() {
        super();
    }

    public EmptyReservationDateException(String message) {
        super(message);
    }

    public EmptyReservationDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyReservationDateException(Throwable cause) {
        super(cause);
    }
}
