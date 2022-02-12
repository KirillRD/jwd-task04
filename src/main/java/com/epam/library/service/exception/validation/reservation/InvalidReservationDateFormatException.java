package com.epam.library.service.exception.validation.reservation;

import com.epam.library.service.exception.validation.ReservationValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of reservation's date is invalid
 */
public class InvalidReservationDateFormatException extends ReservationValidationException {
    @Serial
    private static final long serialVersionUID = 5435181679809129259L;

    public InvalidReservationDateFormatException() {
        super();
    }

    public InvalidReservationDateFormatException(String message) {
        super(message);
    }

    public InvalidReservationDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidReservationDateFormatException(Throwable cause) {
        super(cause);
    }
}
