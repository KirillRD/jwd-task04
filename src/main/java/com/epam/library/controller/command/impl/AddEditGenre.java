package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.command.util.Util;
import com.epam.library.entity.book.Genre;
import com.epam.library.service.GenreService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.GenreException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.genre.EmptyGenreNameException;
import com.epam.library.service.exception.genre.ExistGenreException;
import com.epam.library.service.exception.genre.InvalidLengthGenreNameException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        LogMessageBuilder logMesBuilder = new LogMessageBuilder(request);

        GenreService genreService = ServiceProvider.getInstance().getGenreService();
        Genre genre = new Genre();
        if (Util.isID(request.getParameter(GENRE_ID))) {
            genre.setId(Integer.parseInt(request.getParameter(GENRE_ID)));
            logger.info(logMesBuilder.build("Genre update started"));
        } else {
            logger.info(logMesBuilder.build("Genre add started"));
        }

        genre.setName(request.getParameter(NAME));
        try {
            if (genre.getId() != 0) {
                genreService.updateGenre(genre);
                logger.info(logMesBuilder.build("Genre update completed"));
            } else {
                genreService.addGenre(genre);
                logger.info(logMesBuilder.build("Genre add completed"));
            }

            RequestProvider.redirect(String.format(RedirectCommand.GENRE_PAGE, ""), request, response);
        } catch (GenreException e) {
            HttpSession session = request.getSession();
            session.setAttribute(GENRE, genre);
            session.setAttribute(MESSAGES, errorMessageBuilder(e));
            if (genre.getId() != 0) {
                logger.info(logMesBuilder.build("The entered data is invalid. Genre was not updated"));
                RequestProvider.redirect(String.format(RedirectCommand.GENRE_PAGE, REDIRECT_GENRE_ID + genre.getId()), request, response);
            } else {
                logger.info(logMesBuilder.build("The entered data is invalid. Genre was not added"));
                RequestProvider.redirect(String.format(RedirectCommand.GENRE_PAGE, ""), request, response);
            }
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error adding/updating genre data"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }

    private List<String> errorMessageBuilder(GenreException exception) {
        List<String> messages = new ArrayList<>();
        for (GenreException e : exception.getExceptions()) {
            messages.add(exceptionMessages.get(e.getClass().getSimpleName()));
        }
        return messages;
    }
}
