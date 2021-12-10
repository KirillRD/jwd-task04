package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Reservation;

public interface ReservationDAO {
    void addReservation(Reservation reservation) throws DAOException;
    Reservation getReservation(int reservationID) throws DAOException;
    void updateReservation(Reservation reservation) throws DAOException;
    void deleteReservation(Reservation reservation) throws DAOException;
}
