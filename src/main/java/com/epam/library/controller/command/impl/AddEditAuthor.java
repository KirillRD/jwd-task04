package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.command.util.Util;
import com.epam.library.entity.book.Author;
import com.epam.library.service.AuthorService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.AuthorException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.author.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddEditAuthor implements Command {
    private static final Logger logger = Logger.getLogger(AddEditAuthor.class.getName());

    private static final String AUTHOR_ID = "author_id";
    private static final String AUTHOR = "author";

    private static final String LAST_NAME = "last_name";
    private static final String FIRST_NAME = "first_name";
    private static final String FATHER_NAME = "father_name";

    private static final String REDIRECT_AUTHOR_ID = "&author_id=";
    private static final String MESSAGES = "messages";
    private static final Map<String, String> exceptionMessages = Map.ofEntries(
            Map.entry(EmptyAuthorFirstNameException.class.getSimpleName(), "error-empty-first-name"),
            Map.entry(EmptyAuthorLastNameException.class.getSimpleName(), "error-empty-last-name"),
            Map.entry(ExistAuthorException.class.getSimpleName(), "error-exist-author"),
            Map.entry(InvalidLengthAuthorFatherNameException.class.getSimpleName(), "error-length-father-name"),
            Map.entry(InvalidLengthAuthorFirstNameException.class.getSimpleName(), "error-length-first-name"),
            Map.entry(InvalidLengthAuthorLastNameException.class.getSimpleName(), "error-length-last-name")
    );

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogMessageBuilder logMesBuilder = new LogMessageBuilder(request);

        AuthorService authorService = ServiceProvider.getInstance().getAuthorService();
        Author author = new Author();
        if (Util.isID(request.getParameter(AUTHOR_ID))) {
            author.setId(Integer.parseInt(request.getParameter(AUTHOR_ID)));
            logger.info(logMesBuilder.build("Author update started"));
        } else {
            logger.info(logMesBuilder.build("Author add started"));
        }

        author.setLastName(request.getParameter(LAST_NAME));
        author.setFirstName(request.getParameter(FIRST_NAME));
        author.setFatherName(request.getParameter(FATHER_NAME));
        try {
            if (author.getId() != 0) {
                authorService.updateAuthor(author);
                logger.info(logMesBuilder.build("Author update completed"));
            } else {
                authorService.addAuthor(author);
                logger.info(logMesBuilder.build("Author add completed"));
            }

            RequestProvider.redirect(String.format(RedirectCommand.AUTHOR_PAGE, ""), request, response);
        } catch (AuthorException e) {
            HttpSession session = request.getSession();
            session.setAttribute(AUTHOR, author);
            session.setAttribute(MESSAGES, errorMessageBuilder(e));
            if (author.getId() != 0) {
                logger.info(logMesBuilder.build("The entered data is invalid. Author was not updated"));
                RequestProvider.redirect(String.format(RedirectCommand.AUTHOR_PAGE, REDIRECT_AUTHOR_ID + author.getId()), request, response);
            } else {
                logger.info(logMesBuilder.build("The entered data is invalid. Author was not added"));
                RequestProvider.redirect(String.format(RedirectCommand.AUTHOR_PAGE, ""), request, response);
            }
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error adding/updating author data"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }

    private List<String> errorMessageBuilder(AuthorException exception) {
        List<String> messages = new ArrayList<>();
        for (AuthorException e : exception.getExceptions()) {
            messages.add(exceptionMessages.get(e.getClass().getSimpleName()));
        }
        return messages;
    }
}
