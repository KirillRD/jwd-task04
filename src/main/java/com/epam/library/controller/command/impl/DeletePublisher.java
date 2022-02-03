package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.service.PublisherService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;

public class DeletePublisher implements Command {
    private static final Logger logger = Logger.getLogger(DeletePublisher.class.getName());

    private static final String PUBLISHER_ID = "publisher_id";
    private static final String MESSAGE = "message";
    private static final String ERROR_DELETE_PUBLISHER = "error-delete-publisher";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Publisher delete started"));

        PublisherService publisherService = ServiceProvider.getInstance().getPublisherService();
        try {
            int publisherID;
            if (Util.isID(request.getParameter(PUBLISHER_ID))) {
                publisherID = Integer.parseInt(request.getParameter(PUBLISHER_ID));
            } else {
                logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Publisher was not deleted"));
                RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            if (!publisherService.deletePublisher(publisherID)) {
                HttpSession session = request.getSession();
                session.setAttribute(MESSAGE, ERROR_DELETE_PUBLISHER);
                logger.info(LogMessageBuilder.message(logMessage, "Publisher was not deleted. The book of this publisher already exists"));
            } else {
                logger.info(LogMessageBuilder.message(logMessage, "Publisher delete completed"));
            }

            RequestProvider.redirect(String.format(RedirectCommand.PUBLISHER_PAGE, ""), request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error deleting publisher data"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
