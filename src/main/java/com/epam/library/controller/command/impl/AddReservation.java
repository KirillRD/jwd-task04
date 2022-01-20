package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddReservation implements Command {

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
        ReservationService reservationService = ServiceProvider.getInstance().getReservationService();
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
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
            } else {
                session.setAttribute(RESERVATION_MESSAGE, RESERVATION_UNSUCCESSFUL);
            }

            RequestProvider.redirect(String.format(RedirectCommand.RESERVATION_PAGE, bookID), request, response);
        } catch (ReservationException e) {
            session.setAttribute(RESERVATION, reservation);
            session.setAttribute(MESSAGES, errorMessageBuilder(e));
            RequestProvider.redirect(String.format(RedirectCommand.RESERVATION_PAGE, bookID), request, response);
        } catch (ServiceException e) {
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
