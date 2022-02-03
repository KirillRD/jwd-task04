package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Reservation;

import java.util.List;

public interface ReservationDAO {
    boolean addReservation(Reservation reservation) throws DAOException;
    void updateReservation(List<String> reservations, String status) throws DAOException;
    boolean deleteReservation(int reservationID) throws DAOException;
}
