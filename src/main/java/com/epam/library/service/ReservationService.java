package com.epam.library.service;

import com.epam.library.entity.Reservation;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

/**
 * Reservation service interface
 */
public interface ReservationService {
    /**
     * Returns true if reservation was added successfully
     * @param reservation to be added
     * @return true if reservation was added successfully
     * @throws ServiceException if a data processing error occurs
     */
    boolean addReservation(Reservation reservation) throws ServiceException;

    /**
     * Returns true if book reservation update has been completed successfully
     * @param reservations whose data needs to be updated
     * @param status reservation status
     * @return true if book reservation update has been completed successfully
     * @throws ServiceException if a data processing error occurs
     */
    boolean updateReservation(List<String> reservations, String status) throws ServiceException;

    /**
     * Returns true if reservation was deleted successfully
     * @param reservationID ID of the reservation to be deleted
     * @return true if reservation was deleted successfully
     * @throws ServiceException if a data processing error occurs
     */
    boolean deleteReservation(int reservationID) throws ServiceException;
}
