package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.command.util.Util;
import com.epam.library.entity.book.Publisher;
import com.epam.library.service.PublisherService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class PublisherPage implements Command {
    private static final Logger logger = Logger.getLogger(PublisherPage.class.getName());

    private static final String PUBLISHERS = "publishers";
    private static final String PUBLISHER_ID = "publisher_id";
    private static final String PUBLISHER = "publisher";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogMessageBuilder logMesBuilder = new LogMessageBuilder(request);
        logger.info(logMesBuilder.build("Publisher list build started"));

        PublisherService publisherService = ServiceProvider.getInstance().getPublisherService();

        try {
            List<Publisher> publishers = publisherService.getPublishersList();
            request.setAttribute(PUBLISHERS, publishers);
            if (Util.isID(request.getParameter(PUBLISHER_ID))) {
                int publisherID = Integer.parseInt(request.getParameter(PUBLISHER_ID));
                Publisher publisher = publisherService.getPublisher(publisherID);
                if (publisher == null) {
                    logger.error(logMesBuilder.build("Invalid page attributes. Publisher was not found"));
                    RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                    return;
                }
                HttpSession session = request.getSession();
                if (session.getAttribute(PUBLISHER) == null) {
                    session.setAttribute(PUBLISHER, publisher);
                }
            }
            logger.info(logMesBuilder.build("Publisher list building completed"));

            RequestProvider.forward(PagePath.PUBLISHER_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error getting data for publisher list"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
