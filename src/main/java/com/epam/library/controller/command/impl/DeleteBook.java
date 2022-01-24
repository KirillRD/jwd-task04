package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.command.util.Util;
import com.epam.library.service.*;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;

public class DeleteBook implements Command {
    private static final Logger logger = Logger.getLogger(DeleteBook.class.getName());

    private static final String BOOK_ID = "book_id";
    private static final String MESSAGE = "message";
    private static final String ERROR_DELETE_BOOK = "error-delete-book";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogMessageBuilder logMesBuilder = new LogMessageBuilder(request);
        logger.info(logMesBuilder.build("Book delete started"));

        BookService bookService = ServiceProvider.getInstance().getBookService();

        int bookID;
        if (Util.isID(request.getParameter(BOOK_ID))) {
            bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        } else {
            logger.error(logMesBuilder.build("Invalid page attributes. Book was not deleted"));
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
            return;
        }

        try {
            if (!bookService.deleteBook(bookID)) {
                HttpSession session = request.getSession();
                session.setAttribute(MESSAGE, ERROR_DELETE_BOOK);
                logger.info(logMesBuilder.build("Book was not deleted. Book has instances"));
            } else {
                logger.info(logMesBuilder.build("Book delete completed"));
            }

            RequestProvider.redirect(RedirectCommand.BOOK_LIST_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error deleting book data"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
