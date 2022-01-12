package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
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

import java.io.IOException;
import java.util.List;

public class GoToReservationPage implements Command {

    private static final String BOOK_INFO = "book_info";
    private static final String READER_RESERVATION = "reader_reservation";
    private static final String BOOK_ID = "book_id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookCatalog bookInfo;
        List<ReaderReservation> readerReservationList;
        ReaderService readerService = ServiceProvider.getInstance().getReaderService();
        BookCatalogService bookCatalogService = ServiceProvider.getInstance().getBookCatalogService();

        try {
            SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
            int userID = sessionUser.getId();
            int bookID = Integer.parseInt(request.getParameter(BOOK_ID));

            bookInfo = bookCatalogService.getBookCatalog(bookID);
            request.setAttribute(BOOK_INFO, bookInfo);

            readerReservationList = readerService.getReaderReservationList(userID);
            request.setAttribute(READER_RESERVATION, readerReservationList);
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.forward(PagePath.RESERVATION_PAGE, request, response);
    }
}
