package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.entity.book.dictionary.Genre;
import com.epam.library.service.GenreService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ValidationException;
import com.epam.library.service.exception.validation.GenreValidationException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.validation.genre.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Command to add/edit book genre
 */
public class AddEditGenre implements Command {
    private static final Logger logger = Logger.getLogger(AddEditGenre.class.getName());

    private static final String GENRE_ID = "genre_id";
    private static final String GENRE = "genre";

    private static final String NAME = "name";

    private static final String REDIRECT_GENRE_ID = "&genre_id=";
    private static final String MESSAGES = "messages";
    private static final Map<String, String> exceptionMessages = Map.ofEntries(
            Map.entry(EmptyGenreNameException.class.getSimpleName(), "error-empty-genre-name"),
            Map.entry(ExistGenreException.class.getSimpleName(), "error-exist-genre"),
            Map.entry(InvalidLengthGenreNameException.class.getSimpleName(), "error-length-genre-name")
    );

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);

        GenreService genreService = ServiceProvider.getInstance().getGenreService();
        Genre genre = new Genre();
        if (Util.isID(request.getParameter(GENRE_ID))) {
            genre.setId(Integer.parseInt(request.getParameter(GENRE_ID)));
            logger.info(LogMessageBuilder.message(logMessage, "Genre update started"));
        } else {
            logger.info(LogMessageBuilder.message(logMessage, "Genre add started"));
        }

        genre.setName(request.getParameter(NAME));
        try {
            if (genre.getId() != 0) {
                genreService.updateGenre(genre);
                logger.info(LogMessageBuilder.message(logMessage, "Genre update completed"));
            } else {
                genreService.addGenre(genre);
                logger.info(LogMessageBuilder.message(logMessage, "Genre add completed"));
            }

            RequestManager.redirect(String.format(RedirectCommand.GENRE_PAGE, ""), request, response);
        } catch (GenreValidationException e) {
            HttpSession session = request.getSession();
            session.setAttribute(GENRE, genre);
            session.setAttribute(MESSAGES, errorMessageBuilder(e));
            if (genre.getId() != 0) {
                logger.info(LogMessageBuilder.message(logMessage, "The entered data is invalid. Genre was not updated"));
                RequestManager.redirect(String.format(RedirectCommand.GENRE_PAGE, REDIRECT_GENRE_ID + genre.getId()), request, response);
            } else {
                logger.info(LogMessageBuilder.message(logMessage, "The entered data is invalid. Genre was not added"));
                RequestManager.redirect(String.format(RedirectCommand.GENRE_PAGE, ""), request, response);
            }
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error adding/updating genre data"), e);
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }

    private List<String> errorMessageBuilder(GenreValidationException exception) {
        List<String> messages = new ArrayList<>();
        for (ValidationException e : exception.getExceptions()) {
            messages.add(exceptionMessages.get(e.getClass().getSimpleName()));
        }
        return messages;
    }
}
