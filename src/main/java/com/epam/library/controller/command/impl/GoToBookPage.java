package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.entity.book.catalog.BookCatalog;
import com.epam.library.entity.book.catalog.BookReview;
import com.epam.library.service.BookCatalogService;
import com.epam.library.service.BookReviewService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class GoToBookPage implements Command {

    private static final String BOOK_ID = "book_id";
    private static final String BOOK_INFO = "book_info";
    private static final String BOOK_REVIEW = "book_review";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookCatalog bookInfo;
        List<BookReview> bookReviews;
        BookCatalogService bookCatalogService = ServiceProvider.getInstance().getBookCatalogService();
        BookReviewService bookReviewService = ServiceProvider.getInstance().getBookReviewService();

        int id = Integer.parseInt(request.getParameter(BOOK_ID));

        try {
            bookInfo = bookCatalogService.getBookCatalog(id);
            request.setAttribute(BOOK_INFO, bookInfo);

            bookReviews = bookReviewService.getBookReviews(id);
            request.setAttribute(BOOK_REVIEW, bookReviews);
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.forward(PagePath.BOOK_PAGE, request, response);
    }
}
