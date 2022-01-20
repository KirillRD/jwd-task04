package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Reservation;
import com.epam.library.entity.reservation.ReservationInfo;

import java.util.List;

public interface ReservationDAO {
    boolean addReservation(ReservationInfo reservation) throws DAOException;
    Reservation getReservation(int reservationID) throws DAOException;
    void updateReservation(List<String> reservations, String status) throws DAOException;
    boolean deleteReservation(int reservationID) throws DAOException;
}
