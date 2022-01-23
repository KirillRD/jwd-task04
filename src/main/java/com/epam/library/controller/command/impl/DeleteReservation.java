package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.command.util.Util;
import com.epam.library.service.ReservationService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;

public class DeleteReservation implements Command {
    private static final Logger logger = Logger.getLogger(DeleteReservation.class.getName());
    private LogMessageBuilder logMesBuilder;

    private static final String RESERVATION_ID = "reservation_id";
    private static final String MESSAGE = "message";
    private static final String ERROR_DELETE_RESERVATION = "error-delete-reservation";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logMesBuilder = new LogMessageBuilder(request);
        logger.info(logMesBuilder.build("Reservation delete started"));

        ReservationService reservationService = ServiceProvider.getInstance().getReservationService();

        int reservationID;
        if (Util.isID(request.getParameter(RESERVATION_ID))) {
            reservationID = Integer.parseInt(request.getParameter(RESERVATION_ID));
        } else {
            logger.error(logMesBuilder.build("Invalid page attributes. Reservation was not deleted"));
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
            return;
        }

        try {
            if (!reservationService.deleteReservation(reservationID)) {
                HttpSession session = request.getSession();
                session.setAttribute(MESSAGE, ERROR_DELETE_RESERVATION);
                logger.info(logMesBuilder.build("Reservation was not deleted. Book is no longer in the RESERVED status"));
            } else {
                logger.info(logMesBuilder.build("Reservation deleting completed"));
            }

            RequestProvider.redirect(String.format(RedirectCommand.USER_PAGE, ""), request, response);
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error deleting reservation data"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
