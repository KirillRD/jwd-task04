package com.epam.library.service.exception.reservation;

import com.epam.library.service.exception.ReservationException;

public class InvalidHallFormatException extends ReservationException {
    public InvalidHallFormatException() {
        super();
    }

    public InvalidHallFormatException(String message) {
        super(message);
    }

    public InvalidHallFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidHallFormatException(Throwable cause) {
        super(cause);
    }
}
