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
import java.util.Arrays;
import java.util.List;

public class ReaderReservationOperation implements Command {

    private static final String READER_ID = "reader_id";

    private static final String RESERVATION_OPERATION = "reservation_operation";
    private static final String CHECK_RESERVATION_OPERATION = "check_reservation_operation";

    private static final String RESERVATION_MESSAGE = "reservation_message";
    private static final String ERROR_ISSUANCE_RESERVATION = "error-issuance-reservation";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReservationService reservationService = ServiceProvider.getInstance().getReservationService();

        try {
            if (request.getParameter(RESERVATION_OPERATION) != null && request.getParameterValues(CHECK_RESERVATION_OPERATION) != null) {
                List<String> reservations = Arrays.asList(request.getParameterValues(CHECK_RESERVATION_OPERATION));
                String status = request.getParameter(RESERVATION_OPERATION);
                if (!reservationService.updateReservation(reservations, status)) {
                    HttpSession session = request.getSession();
                    session.setAttribute(RESERVATION_MESSAGE, ERROR_ISSUANCE_RESERVATION);
                }
            }
            int readerID = Integer.parseInt(request.getParameter(READER_ID));

            RequestProvider.redirect(String.format(RedirectCommand.READER_PAGE, readerID), request, response);
        } catch (ServiceException e) {
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
