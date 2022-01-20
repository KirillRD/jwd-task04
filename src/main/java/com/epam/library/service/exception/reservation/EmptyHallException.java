package com.epam.library.service.exception.reservation;

import com.epam.library.service.exception.ReservationException;

public class EmptyHallException extends ReservationException {
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
