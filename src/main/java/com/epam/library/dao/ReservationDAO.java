package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Reservation;

import java.util.List;

/**
 * Reservation DAO interface
 */
public interface ReservationDAO {
    /**
     * Returns true if reservation was added successfully
     * @param reservation to be added
     * @return true if reservation was added successfully
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean addReservation(Reservation reservation) throws DAOException;

    /**
     * Updating the reservation's data
     * @param reservations whose data needs to be updated
     * @param status reservation status
     * @throws DAOException if an error occurred while accessing the database
     */
    void updateReservation(List<String> reservations, String status) throws DAOException;

    /**
     * Returns true if reservation was deleted successfully
     * @param reservationID ID of the reservation to be deleted
     * @return true if reservation was deleted successfully
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean deleteReservation(int reservationID) throws DAOException;
}
