package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.command.util.Util;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.reservation.ReservationInfo;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.ReservationService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ReservationException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.reservation.EmptyHallException;
import com.epam.library.service.exception.reservation.EmptyReservationDateException;
import com.epam.library.service.exception.reservation.InvalidHallFormatException;
import com.epam.library.service.exception.reservation.InvalidReservationDateFormatException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddReservation implements Command {
    private static final Logger logger = Logger.getLogger(AddReservation.class.getName());

    private static final String RESERVATION_DATE = "reservation_date";
    private static final String BOOK_ID = "book_id";
    private static final String HALL_ID = "hall_id";
    private static final String RESERVATION_MESSAGE = "reservation_message";
    private static final String RESERVATION_SUCCESS = "success-message";
    private static final String RESERVATION_UNSUCCESSFUL = "unsuccessful-message";
    private static final String RESERVATION = "reservation";
    private static final String MESSAGES = "messages";
    private static final Map<String, String> exceptionMessages = Map.ofEntries(
            Map.entry(EmptyHallException.class.getSimpleName(), "error-empty-hall"),
            Map.entry(EmptyReservationDateException.class.getSimpleName(), "error-empty-reservation-date"),
            Map.entry(InvalidHallFormatException.class.getSimpleName(), "error-hall-format"),
            Map.entry(InvalidReservationDateFormatException.class.getSimpleName(), "error-reservation-date-format")
    );

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogMessageBuilder logMesBuilder = new LogMessageBuilder(request);
        logger.info(logMesBuilder.build("Reservation adding started"));

        ReservationService reservationService = ServiceProvider.getInstance().getReservationService();

        int bookID;
        if (Util.isID(request.getParameter(BOOK_ID))) {
            bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        } else {
            logger.error(logMesBuilder.build("Invalid page attributes. Reservation was not added"));
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
            return;
        }
        ReservationInfo reservation = new ReservationInfo();
        HttpSession session = request.getSession();
        try {
            SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
            int userID = sessionUser.getId();

            reservation.setReaderID(userID);
            reservation.setBookID(bookID);
            reservation.setDate(request.getParameter(RESERVATION_DATE));
            reservation.setHallID(request.getParameter(HALL_ID));

            if (reservationService.addReservation(reservation)) {
                session.setAttribute(RESERVATION_MESSAGE, RESERVATION_SUCCESS);
                logger.info(logMesBuilder.build("Adding reservation completed"));
            } else {
                session.setAttribute(RESERVATION_MESSAGE, RESERVATION_UNSUCCESSFUL);
                logger.info(logMesBuilder.build("Reservation was not added. There are no free instances"));
            }

            RequestProvider.redirect(String.format(RedirectCommand.RESERVATION_PAGE, bookID), request, response);
        } catch (ReservationException e) {
            session.setAttribute(RESERVATION, reservation);
            session.setAttribute(MESSAGES, errorMessageBuilder(e));
            logger.info(logMesBuilder.build("The entered data is invalid. Reservation was not added"));
            RequestProvider.redirect(String.format(RedirectCommand.RESERVATION_PAGE, bookID), request, response);
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error adding reservation data"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }

    private List<String> errorMessageBuilder(ReservationException exception) {
        List<String> messages = new ArrayList<>();
        for (ReservationException e : exception.getExceptions()) {
            messages.add(exceptionMessages.get(e.getClass().getSimpleName()));
        }
        return messages;
    }
}
