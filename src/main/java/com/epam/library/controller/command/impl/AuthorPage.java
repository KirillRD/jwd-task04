package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.PagePath;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.entity.book.dictionary.Author;
import com.epam.library.service.AuthorService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class AuthorPage implements Command {
    private static final Logger logger = Logger.getLogger(AuthorPage.class.getName());

    private static final String AUTHORS = "authors";
    private static final String AUTHOR_ID = "author_id";
    private static final String AUTHOR = "author";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Author list build started"));

        AuthorService authorService = ServiceProvider.getInstance().getAuthorService();

        try {
            List<Author> authors = authorService.getAuthorsList();
            request.setAttribute(AUTHORS, authors);
            if (Util.isID(request.getParameter(AUTHOR_ID))) {
                int authorID = Integer.parseInt(request.getParameter(AUTHOR_ID));
                Author author = authorService.getAuthor(authorID);
                if (author == null) {
                    logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Author was not found"));
                    RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                    return;
                }
                HttpSession session = request.getSession();
                if (session.getAttribute(AUTHOR) == null) {
                    session.setAttribute(AUTHOR, author);
                }
            }
            logger.info(LogMessageBuilder.message(logMessage, "Author list building completed"));

            RequestProvider.forward(PagePath.AUTHOR_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error getting data for author list"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
