package com.epam.library.service.exception.reservation;

import com.epam.library.service.exception.ReservationException;

public class EmptyReservationDateException extends ReservationException {
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
