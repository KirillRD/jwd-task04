package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.PagePath;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.entity.book.catalog.BookCatalog;
import com.epam.library.entity.review.BookReview;
import com.epam.library.service.BookCatalogService;
import com.epam.library.service.ReviewService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Command to go to book page
 */
public class GoToBookPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToBookPage.class.getName());

    private static final String BOOK_ID = "book_id";
    private static final String BOOK_INFO = "book_info";
    private static final String BOOK_REVIEW = "book_review";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Going to book page started"));

        BookCatalog bookInfo;
        List<BookReview> bookReviews;
        BookCatalogService bookCatalogService = ServiceProvider.getInstance().getBookCatalogService();
        ReviewService reviewService = ServiceProvider.getInstance().getReviewService();

        try {
            int bookID;
            if (Util.isID(request.getParameter(BOOK_ID))) {
                bookID = Integer.parseInt(request.getParameter(BOOK_ID));
            } else {
                logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Going to book page is failed"));
                RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            bookInfo = bookCatalogService.getBookCatalog(bookID);
            if (bookInfo == null) {
                logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Going to book page is failed"));
                RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            request.setAttribute(BOOK_INFO, bookInfo);

            bookReviews = reviewService.getBookReviews(bookID);
            request.setAttribute(BOOK_REVIEW, bookReviews);
            logger.info(LogMessageBuilder.message(logMessage, "Going to book page was completed"));

            RequestManager.forward(PagePath.BOOK_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error in data while going to book page"), e);
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
