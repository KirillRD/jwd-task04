package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.PagePath;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.entity.book.dictionary.Genre;
import com.epam.library.service.GenreService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Command to go to the book's genre page and crossing genre editing mode
 */
public class GenrePage implements Command {
    private static final Logger logger = Logger.getLogger(GenrePage.class.getName());

    private static final String GENRES = "genres";
    private static final String GENRE_ID = "genre_id";
    private static final String GENRE = "genre";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Genre list build started"));

        GenreService genreService = ServiceProvider.getInstance().getGenreService();

        try {
            List<Genre> genres = genreService.getGenresList();
            request.setAttribute(GENRES, genres);
            if (Util.isID(request.getParameter(GENRE_ID))) {
                int genreID = Integer.parseInt(request.getParameter(GENRE_ID));
                Genre genre = genreService.getGenre(genreID);
                if (genre == null) {
                    logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Genre was not found"));
                    RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                    return;
                }
                HttpSession session = request.getSession();
                if (session.getAttribute(GENRE) == null) {
                    session.setAttribute(GENRE, genre);
                }
            }
            logger.info(LogMessageBuilder.message(logMessage, "Genre list building completed"));

            RequestManager.forward(PagePath.GENRE_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error getting data for genre list"), e);
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
