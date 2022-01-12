package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.entity.Issuance;
import com.epam.library.entity.Reservation;
import com.epam.library.entity.reservation.ReservationStatus;
import com.epam.library.service.IssuanceService;
import com.epam.library.service.ReservationService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ReaderReservationOperation implements Command {

    private static final String READER_ID = "reader_id";

    private static final String RESERVATION_OPERATION = "reservation_operation";
    private static final String CHECK_RESERVATION_OPERATION = "check_reservation_operation";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IssuanceService issuanceService = ServiceProvider.getInstance().getIssuanceService();
        ReservationService reservationService = ServiceProvider.getInstance().getReservationService();

        try {

            if (request.getParameter(RESERVATION_OPERATION) != null && request.getParameterValues(CHECK_RESERVATION_OPERATION) != null) {
                for (String id : request.getParameterValues(CHECK_RESERVATION_OPERATION)) {
                    Reservation reservation = reservationService.getReservation(Integer.parseInt(id));
                    reservation.setStatus(ReservationStatus.valueOf(request.getParameter(RESERVATION_OPERATION)));
                    if (ReservationStatus.ISSUED == reservation.getStatus()) {
                        Issuance issuance = new Issuance();
                        issuance.setInstanceID(reservation.getInstanceID());
                        issuance.setReaderID(reservation.getReaderID());
                        issuanceService.addIssuance(issuance);
                    }
                    reservationService.updateReservation(reservation);
                }
            }

        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }
        int readerID = Integer.parseInt(request.getParameter(READER_ID));

        RequestProvider.redirect(String.format(RedirectCommand.READER_PAGE, readerID), request, response);
    }
}
