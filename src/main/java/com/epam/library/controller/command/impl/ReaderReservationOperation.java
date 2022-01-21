package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
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
import java.util.Arrays;
import java.util.List;

public class ReaderReservationOperation implements Command {
    private static final Logger logger = Logger.getLogger(ReaderReservationOperation.class.getName());

    private static final String READER_ID = "reader_id";

    private static final String RESERVATION_OPERATION = "reservation_operation";
    private static final String CHECK_RESERVATION_OPERATION = "check_reservation_operation";

    private static final String RESERVATION_MESSAGE = "reservation_message";
    private static final String ERROR_ISSUANCE_RESERVATION = "error-issuance-reservation";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReservationService reservationService = ServiceProvider.getInstance().getReservationService();
        logger.info(logMessageBuilder("Book reservation operation started", request));

        int readerID;
        if (Util.isID(request.getParameter(READER_ID))) {
            readerID = Integer.parseInt(request.getParameter(READER_ID));
        } else {
            logger.error(logMessageBuilder("Invalid page attributes. Book reservation operation was failed", request));
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
            return;
        }
        try {
            if (request.getParameter(RESERVATION_OPERATION) != null && request.getParameterValues(CHECK_RESERVATION_OPERATION) != null) {
                List<String> reservations = Arrays.asList(request.getParameterValues(CHECK_RESERVATION_OPERATION));
                String status = request.getParameter(RESERVATION_OPERATION);
                if (!reservationService.updateReservation(reservations, status)) {
                    HttpSession session = request.getSession();
                    session.setAttribute(RESERVATION_MESSAGE, ERROR_ISSUANCE_RESERVATION);
                    logger.info(logMessageBuilder("Book reservation operation was failed. Incorrect data format", request));
                } else {
                    logger.info(logMessageBuilder("Book reservation operation completed", request));
                }
            }

            RequestProvider.redirect(String.format(RedirectCommand.READER_PAGE, readerID), request, response);
        } catch (ServiceException e) {
            logger.error(logMessageBuilder("Error of the book reservation operation data", request), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
