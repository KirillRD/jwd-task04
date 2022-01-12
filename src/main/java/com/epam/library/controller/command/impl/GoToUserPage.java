package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.User;
import com.epam.library.entity.issuance.ReaderIssuance;
import com.epam.library.entity.reservation.ReaderReservation;
import com.epam.library.entity.user.Reader;
import com.epam.library.entity.user.Role;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.*;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class GoToUserPage implements Command {

    private static final String USER_ID = "user_id";
    private static final String USER = "user";

    private static final String READER = "reader";
    private static final String READER_ISSUANCE = "reader_issuance";
    private static final String READER_ISSUANCE_HISTORY = "reader_issuance_history";
    private static final String READER_RESERVATION = "reader_reservation";
    private static final String READER_RESERVATION_HISTORY = "reader_reservation_history";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = ServiceProvider.getInstance().getUserService();
        try {
            int id;
            SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
            if (request.getParameter(USER_ID) != null && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
                id = Integer.parseInt(request.getParameter(USER_ID));
            } else {
                id = sessionUser.getId();
            }
            User user = userService.getUser(id);
            request.setAttribute(USER, user);

            if (Role.READER == user.getRole()) {
                ReaderService readerService = ServiceProvider.getInstance().getReaderService();
                Reader reader = readerService.getReader(id);
                List<ReaderIssuance> readerIssuanceList = readerService.getReaderIssuanceList(id);
                List<ReaderIssuance> readerIssuanceHistoryList = readerService.getReaderIssuanceHistoryList(id);
                List<ReaderReservation> readerReservationList = readerService.getReaderReservationList(id);
                List<ReaderReservation> readerReservationHistoryList = readerService.getReaderReservationHistoryList(id);
                request.setAttribute(READER, reader);
                request.setAttribute(READER_ISSUANCE, readerIssuanceList);
                request.setAttribute(READER_ISSUANCE_HISTORY, readerIssuanceHistoryList);
                request.setAttribute(READER_RESERVATION, readerReservationList);
                request.setAttribute(READER_RESERVATION_HISTORY, readerReservationHistoryList);
            }

        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.forward(PagePath.USER_PAGE, request, response);
    }
}
