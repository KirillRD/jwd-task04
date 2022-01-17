package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.service.*;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class DeleteBook implements Command {

    private static final String BOOK_ID = "book_id";
    private static final String MESSAGE = "message";
    private static final String ERROR_DELETE_BOOK = "error-delete-book";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = ServiceProvider.getInstance().getBookService();
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));

        try {
            if (!bookService.deleteBook(bookID)) {
                HttpSession session = request.getSession();
                session.setAttribute(MESSAGE, ERROR_DELETE_BOOK);
            }

            RequestProvider.redirect(RedirectCommand.BOOK_LIST_PAGE, request, response);
        } catch (ServiceException e) {
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
