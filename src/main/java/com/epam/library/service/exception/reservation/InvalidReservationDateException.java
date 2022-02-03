package com.epam.library.service.exception.reservation;

import com.epam.library.service.exception.ReservationException;

public class InvalidReservationDateException extends ReservationException {
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
