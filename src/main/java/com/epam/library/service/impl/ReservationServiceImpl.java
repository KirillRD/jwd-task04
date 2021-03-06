package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.ReservationDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Reservation;
import com.epam.library.constant.ReservationStatus;
import com.epam.library.service.ReservationService;
import com.epam.library.service.exception.GeneralException;
import com.epam.library.service.exception.validation.ReservationValidationException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.validation.reservation.*;
import com.epam.library.service.validation.Validator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    private static final ReservationDAO reservationDAO = DAOProvider.getInstance().getReservationDAO();
    private static final Validator validator = Validator.getInstance();

    public ReservationServiceImpl() {}

    @Override
    public boolean addReservation(Reservation reservation) throws ServiceException {

        List<ReservationValidationException> exceptions = new ArrayList<>();
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
            } else if (Date.valueOf(reservation.getDate()).compareTo(Date.valueOf(LocalDate.now())) < 0) {
                exceptions.add(new InvalidReservationDateException());
            }

            if (exceptions.isEmpty()) {
                return reservationDAO.addReservation(reservation);
            } else {
                throw new ReservationValidationException(exceptions);
            }
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public boolean updateReservation(List<String> reservations, String status) throws ServiceException {
        try {
            if (!ReservationStatus.containsReservationStatus(status)) {
                return false;
            }

            for (String reservationID : reservations) {
                if (!validator.isInteger(reservationID)) {
                    return false;
                }
            }

            reservationDAO.updateReservation(reservations, status);
            return true;
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public boolean deleteReservation(int reservationID) throws ServiceException {
        try {
            return reservationDAO.deleteReservation(reservationID);
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }
}
