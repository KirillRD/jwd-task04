package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Reservation;
import com.epam.library.entity.reservation.ReservationInfo;

public interface ReservationDAO {
    boolean addReservation(ReservationInfo reservation) throws DAOException;
    Reservation getReservation(int reservationID) throws DAOException;
    void updateReservation(Reservation reservation) throws DAOException;
    boolean deleteReservation(int reservationID) throws DAOException;
}
