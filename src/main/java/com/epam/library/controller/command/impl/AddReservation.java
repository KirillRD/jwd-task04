package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.Reservation;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.ReservationService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

public class AddReservation implements Command {

    private static final String RESERVATION_DATE = "reservation_date";
    private static final String BOOK_ID = "book_id";
    private static final String HALL_ID = "hall_id";
    private static final String RESERVATION_SUCCESS = "success-message";
    private static final String RESERVATION_UNSUCCESSFUL = "unsuccessful-message";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReservationService reservationService = ServiceProvider.getInstance().getReservationService();
        String message = null;
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        try {
            SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
            int userID = sessionUser.getId();

            Reservation reservation = new Reservation();
            reservation.setReaderID(userID);
            Date date = Date.valueOf(request.getParameter(RESERVATION_DATE));
            reservation.setDate(date);
            int hallID = Integer.parseInt(request.getParameter(HALL_ID));
            if (reservationService.addReservation(reservation, bookID, hallID)) {
                message = RESERVATION_SUCCESS;
            } else {
                message = RESERVATION_UNSUCCESSFUL;
            }
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.redirect(String.format(RedirectCommand.RESERVATION_PAGE, bookID, message), request, response);
    }
}
