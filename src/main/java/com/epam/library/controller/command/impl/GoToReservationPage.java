package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.PagePath;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.book.catalog.BookCatalog;
import com.epam.library.entity.reservation.ReaderReservation;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.BookCatalogService;
import com.epam.library.service.ReaderService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class GoToReservationPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToReservationPage.class.getName());

    private static final String BOOK_INFO = "book_info";
    private static final String READER_RESERVATION = "reader_reservation";
    private static final String BOOK_ID = "book_id";
    private static final String RESERVATION_MESSAGE = "reservation_message";
    private static final String MESSAGE = "message";
    private static final String ERROR_RESERVATION = "error-reservation";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Going to reservation page started"));

        BookCatalog bookInfo;
        List<ReaderReservation> readerReservationList;
        ReaderService readerService = ServiceProvider.getInstance().getReaderService();
        BookCatalogService bookCatalogService = ServiceProvider.getInstance().getBookCatalogService();

        try {
            SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
            int userID = sessionUser.getId();
            int bookID;
            if (Util.isID(request.getParameter(BOOK_ID))) {
                bookID = Integer.parseInt(request.getParameter(BOOK_ID));
            } else {
                logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Going to reservation page is failed"));
                RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }

            bookInfo = bookCatalogService.getBookCatalog(bookID);
            if (bookInfo == null) {
                logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Going to reservation page is failed"));
                RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            HttpSession session = request.getSession();
            if (bookInfo.getHallFreeInstanceCatalogList().isEmpty() && session.getAttribute(RESERVATION_MESSAGE) == null) {
                logger.error(LogMessageBuilder.message(logMessage, "Going to reservation page is failed. There are no free instances for this book"));
                session.setAttribute(MESSAGE, ERROR_RESERVATION);
                RequestProvider.redirect(String.format(RedirectCommand.BOOK_PAGE, bookID), request, response);
                return;
            }
            request.setAttribute(BOOK_INFO, bookInfo);

            readerReservationList = readerService.getReaderReservationList(userID);
            request.setAttribute(READER_RESERVATION, readerReservationList);

            logger.info(LogMessageBuilder.message(logMessage, "Going to reservation page was completed"));
            RequestProvider.forward(PagePath.RESERVATION_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error in data while going to reservation page"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
