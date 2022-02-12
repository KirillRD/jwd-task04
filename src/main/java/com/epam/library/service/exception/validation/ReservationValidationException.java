package com.epam.library.service.exception.validation;

import com.epam.library.service.exception.ValidationException;

import java.io.Serial;
import java.util.List;

/**
 * Thrown when the reservation's data is not valid
 */
public class ReservationValidationException extends ValidationException {
    @Serial
    private static final long serialVersionUID = 2981362771728929681L;

    public ReservationValidationException() {
        super();
    }

    public ReservationValidationException(String message) {
        super(message);
    }

    public ReservationValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationValidationException(Throwable cause) {
        super(cause);
    }

    public ReservationValidationException(List<? extends ValidationException> exceptions) {
        super(exceptions);
    }
}
