package com.epam.library.service.exception.validation.reservation;

import com.epam.library.service.exception.validation.ReservationValidationException;

import java.io.Serial;

/**
 * Validation exception which is used when format of reservation's hall is invalid
 */
public class InvalidHallFormatException extends ReservationValidationException {
    @Serial
    private static final long serialVersionUID = 6217301402289594737L;

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
