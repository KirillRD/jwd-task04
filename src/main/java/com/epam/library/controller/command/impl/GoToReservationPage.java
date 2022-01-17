package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
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

import java.io.IOException;
import java.util.List;

public class GoToReservationPage implements Command {

    private static final String BOOK_INFO = "book_info";
    private static final String READER_RESERVATION = "reader_reservation";
    private static final String BOOK_ID = "book_id";
    private static final String RESERVATION_MESSAGE = "reservation_message";
    private static final String MESSAGE = "message";
    private static final String ERROR_RESERVATION = "error-reservation";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookCatalog bookInfo;
        List<ReaderReservation> readerReservationList;
        ReaderService readerService = ServiceProvider.getInstance().getReaderService();
        BookCatalogService bookCatalogService = ServiceProvider.getInstance().getBookCatalogService();

        try {
            SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
            int userID = sessionUser.getId();
            if (request.getParameter(BOOK_ID) == null) {
                RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            int bookID = Integer.parseInt(request.getParameter(BOOK_ID));

            bookInfo = bookCatalogService.getBookCatalog(bookID);
            if (bookInfo == null) {
                RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            if (bookInfo.getHallFreeInstanceCatalogList().isEmpty() && request.getParameter(RESERVATION_MESSAGE) == null) {
                HttpSession session = request.getSession();
                session.setAttribute(MESSAGE, ERROR_RESERVATION);
                RequestProvider.redirect(String.format(RedirectCommand.BOOK_PAGE, bookID), request, response);
                return;
            }
            request.setAttribute(BOOK_INFO, bookInfo);

            readerReservationList = readerService.getReaderReservationList(userID);
            request.setAttribute(READER_RESERVATION, readerReservationList);

            RequestProvider.forward(PagePath.RESERVATION_PAGE, request, response);
        } catch (ServiceException e) {
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
