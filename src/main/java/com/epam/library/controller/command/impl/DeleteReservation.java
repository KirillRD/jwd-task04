package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.service.ReservationService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class DeleteReservation implements Command {

    private static final String RESERVATION_ID = "reservation_id";
    private static final String MESSAGE = "message";
    private static final String ERROR_DELETE_RESERVATION = "error-delete-reservation";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReservationService reservationService = ServiceProvider.getInstance().getReservationService();
        int reservationID = Integer.parseInt(request.getParameter(RESERVATION_ID));

        try {
            if (!reservationService.deleteReservation(reservationID)) {
                HttpSession session = request.getSession();
                session.setAttribute(MESSAGE, ERROR_DELETE_RESERVATION);
            }

            RequestProvider.redirect(String.format(RedirectCommand.USER_PAGE, ""), request, response);
        } catch (ServiceException e) {
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
