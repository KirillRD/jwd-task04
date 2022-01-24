package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.command.util.Util;
import com.epam.library.service.AuthorService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;

public class DeleteAuthor implements Command {
    private static final Logger logger = Logger.getLogger(DeleteAuthor.class.getName());

    private static final String AUTHOR_ID = "author_id";
    private static final String MESSAGE = "message";
    private static final String ERROR_DELETE_AUTHOR = "error-delete-author";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogMessageBuilder logMesBuilder = new LogMessageBuilder(request);
        logger.info(logMesBuilder.build("Author delete started"));

        AuthorService authorService = ServiceProvider.getInstance().getAuthorService();
        try {
            int authorID;
            if (Util.isID(request.getParameter(AUTHOR_ID))) {
                authorID = Integer.parseInt(request.getParameter(AUTHOR_ID));
            } else {
                logger.error(logMesBuilder.build("Invalid page attributes. Author was not deleted"));
                RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            if (!authorService.deleteAuthor(authorID)) {
                HttpSession session = request.getSession();
                session.setAttribute(MESSAGE, ERROR_DELETE_AUTHOR);
                logger.info(logMesBuilder.build("Author was not deleted. The book of this author already exists"));
            } else {
                logger.info(logMesBuilder.build("Author delete completed"));
            }

            RequestProvider.redirect(String.format(RedirectCommand.AUTHOR_PAGE, ""), request, response);
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error deleting author data"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
