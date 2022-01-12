package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.ReservationDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Reservation;
import com.epam.library.service.ReservationService;
import com.epam.library.service.exception.ServiceException;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationServiceImpl() {
        reservationDAO = DAOProvider.getInstance().getReservationDAO();
    }

    @Override
    public boolean addReservation(Reservation reservation, int bookID, int hallID) throws ServiceException {
        try {
            return reservationDAO.addReservation(reservation, bookID, hallID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Reservation getReservation(int reservationID) throws ServiceException {
        try {
            return reservationDAO.getReservation(reservationID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateReservation(Reservation reservation) throws ServiceException {
        try {
            reservationDAO.updateReservation(reservation);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteReservation(int reservationID) throws ServiceException {
        try {
            return reservationDAO.deleteReservation(reservationID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
