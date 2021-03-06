package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.service.GenreService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Command to delete book genre
 */
public class DeleteGenre implements Command {
    private static final Logger logger = Logger.getLogger(DeleteGenre.class.getName());

    private static final String GENRE_ID = "genre_id";
    private static final String MESSAGE = "message";
    private static final String ERROR_DELETE_GENRE = "error-delete-genre";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Genre delete started"));

        GenreService genreService = ServiceProvider.getInstance().getGenreService();
        try {
            int genreID;
            if (Util.isID(request.getParameter(GENRE_ID))) {
                genreID = Integer.parseInt(request.getParameter(GENRE_ID));
            } else {
                logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Genre was not deleted"));
                RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            if (!genreService.deleteGenre(genreID)) {
                HttpSession session = request.getSession();
                session.setAttribute(MESSAGE, ERROR_DELETE_GENRE);
                logger.info(LogMessageBuilder.message(logMessage, "Genre was not deleted. The book of this genre already exists"));
            } else {
                logger.info(LogMessageBuilder.message(logMessage, "Genre delete completed"));
            }

            RequestManager.redirect(String.format(RedirectCommand.GENRE_PAGE, ""), request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error deleting genre data"), e);
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
