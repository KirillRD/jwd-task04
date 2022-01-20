package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.ReservationDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Reservation;
import com.epam.library.entity.reservation.ReservationInfo;
import com.epam.library.service.ReservationService;
import com.epam.library.service.exception.ReservationException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.reservation.EmptyHallException;
import com.epam.library.service.exception.reservation.EmptyReservationDateException;
import com.epam.library.service.exception.reservation.InvalidHallFormatException;
import com.epam.library.service.exception.reservation.InvalidReservationDateFormatException;
import com.epam.library.service.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    private static final ReservationDAO reservationDAO = DAOProvider.getInstance().getReservationDAO();
    private static final Validator validator = Validator.getInstance();

    public ReservationServiceImpl() {}

    @Override
    public boolean addReservation(ReservationInfo reservation) throws ServiceException {

        List<ReservationException> exceptions = new ArrayList<>();
        try {
            if (validator.isEmpty(reservation.getHallID())) {
                exceptions.add(new EmptyHallException());
            } else if (!validator.isInteger(reservation.getHallID())) {
                exceptions.add(new InvalidHallFormatException());
            }

            if (validator.isEmpty(reservation.getDate())) {
                exceptions.add(new EmptyReservationDateException());
            } else if (!validator.isDate(reservation.getDate())) {
                exceptions.add(new InvalidReservationDateFormatException());
            }

            if (exceptions.isEmpty()) {
                return reservationDAO.addReservation(reservation);
            } else {
                throw new ReservationException(exceptions);
            }
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
