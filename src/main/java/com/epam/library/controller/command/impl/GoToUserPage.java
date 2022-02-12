package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.PagePath;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.User;
import com.epam.library.entity.issuance.ReaderIssuance;
import com.epam.library.entity.reservation.ReaderReservation;
import com.epam.library.entity.user.Reader;
import com.epam.library.entity.user.constant.Role;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.*;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Command to go to user's page
 */
public class GoToUserPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToUserPage.class.getName());

    private static final String USER_ID = "user_id";
    private static final String USER = "user";

    private static final String READER = "reader";
    private static final String READER_ISSUANCE = "reader_issuance";
    private static final String READER_ISSUANCE_HISTORY = "reader_issuance_history";
    private static final String READER_RESERVATION = "reader_reservation";
    private static final String READER_RESERVATION_HISTORY = "reader_reservation_history";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Going to user page started"));

        UserService userService = ServiceProvider.getInstance().getUserService();
        try {
            int userID;
            SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
            if (Util.isID(request.getParameter(USER_ID)) && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
                logger.info(LogMessageBuilder.message(logMessage, "Admin/Librarian goes to user page"));
                userID = Integer.parseInt(request.getParameter(USER_ID));
            } else {
                logger.info(LogMessageBuilder.message(logMessage, "User goes to self page"));
                userID = sessionUser.getId();
            }
            User user = userService.getUser(userID);
            if (user == null) {
                logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Going to user page is failed"));
                RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            request.setAttribute(USER, user);

            if (Role.READER == user.getRole()) {
                ReaderService readerService = ServiceProvider.getInstance().getReaderService();
                Reader reader = readerService.getReader(userID);
                List<ReaderIssuance> readerIssuanceList = readerService.getReaderIssuanceList(userID);
                List<ReaderIssuance> readerIssuanceHistoryList = readerService.getReaderIssuanceHistoryList(userID);
                List<ReaderReservation> readerReservationList = readerService.getReaderReservationList(userID);
                List<ReaderReservation> readerReservationHistoryList = readerService.getReaderReservationHistoryList(userID);
                request.setAttribute(READER, reader);
                request.setAttribute(READER_ISSUANCE, readerIssuanceList);
                request.setAttribute(READER_ISSUANCE_HISTORY, readerIssuanceHistoryList);
                request.setAttribute(READER_RESERVATION, readerReservationList);
                request.setAttribute(READER_RESERVATION_HISTORY, readerReservationHistoryList);
            }

            logger.info(LogMessageBuilder.message(logMessage, "Going to user page was completed"));
            RequestManager.forward(PagePath.USER_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error in data while going to user page"), e);
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
