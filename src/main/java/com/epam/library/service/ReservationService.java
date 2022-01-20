package com.epam.library.service;

import com.epam.library.entity.Reservation;
import com.epam.library.entity.reservation.ReservationInfo;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface ReservationService {
    boolean addReservation(ReservationInfo reservation) throws ServiceException;
    Reservation getReservation(int reservationID) throws ServiceException;
    boolean updateReservation(List<String> reservations, String status) throws ServiceException;
    boolean deleteReservation(int reservationID) throws ServiceException;
}
