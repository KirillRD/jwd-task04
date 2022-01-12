package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Reservation;

public interface ReservationDAO {
    boolean addReservation(Reservation reservation, int bookID, int hallID) throws DAOException;
    Reservation getReservation(int reservationID) throws DAOException;
    void updateReservation(Reservation reservation) throws DAOException;
    boolean deleteReservation(int reservationID) throws DAOException;
}
