package com.epam.library.service.exception.reservation;

import com.epam.library.service.exception.ReservationException;

public class InvalidReservationDateFormatException extends ReservationException {
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
