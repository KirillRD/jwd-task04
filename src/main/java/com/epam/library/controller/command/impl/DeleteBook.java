package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.service.*;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteBook implements Command {

    private static final String BOOK_ID = "book_id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = ServiceProvider.getInstance().getBookService();
        try {
            if (request.getParameter(BOOK_ID) != null) {
                int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
                bookService.deleteBook(bookID);
            }
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.redirect(RedirectCommand.BOOK_LIST_PAGE, request, response);
    }
}
