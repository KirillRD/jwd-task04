package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.command.util.Util;
import com.epam.library.entity.book.Publisher;
import com.epam.library.service.PublisherService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.PublisherException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.publisher.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddEditPublisher implements Command {
    private static final Logger logger = Logger.getLogger(AddEditPublisher.class.getName());

    private static final String PUBLISHER_ID = "publisher_id";
    private static final String PUBLISHER = "publisher";

    private static final String NAME = "name";
    private static final String CITY = "city";

    private static final String REDIRECT_PUBLISHER_ID = "&publisher_id=";
    private static final String MESSAGES = "messages";
    private static final Map<String, String> exceptionMessages = Map.ofEntries(
            Map.entry(EmptyPublisherCityException.class.getSimpleName(), "error-empty-publisher-city"),
            Map.entry(EmptyPublisherNameException.class.getSimpleName(), "error-empty-publisher-name"),
            Map.entry(ExistPublisherException.class.getSimpleName(), "error-exist-publisher"),
            Map.entry(InvalidLengthPublisherCityException.class.getSimpleName(), "error-length-publisher-city"),
            Map.entry(InvalidLengthPublisherNameException.class.getSimpleName(), "error-length-publisher-name")
    );

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogMessageBuilder logMesBuilder = new LogMessageBuilder(request);

        PublisherService publisherService = ServiceProvider.getInstance().getPublisherService();
        Publisher publisher = new Publisher();
        if (Util.isID(request.getParameter(PUBLISHER_ID))) {
            publisher.setId(Integer.parseInt(request.getParameter(PUBLISHER_ID)));
            logger.info(logMesBuilder.build("Publisher update started"));
        } else {
            logger.info(logMesBuilder.build("Publisher add started"));
        }

        publisher.setName(request.getParameter(NAME));
        publisher.setCity(request.getParameter(CITY));
        try {
            if (publisher.getId() != 0) {
                publisherService.updatePublisher(publisher);
                logger.info(logMesBuilder.build("Publisher update completed"));
            } else {
                publisherService.addPublisher(publisher);
                logger.info(logMesBuilder.build("Publisher add completed"));
            }

            RequestProvider.redirect(String.format(RedirectCommand.PUBLISHER_PAGE, ""), request, response);
        } catch (PublisherException e) {
            HttpSession session = request.getSession();
            session.setAttribute(PUBLISHER, publisher);
            session.setAttribute(MESSAGES, errorMessageBuilder(e));
            if (publisher.getId() != 0) {
                logger.info(logMesBuilder.build("The entered data is invalid. Publisher was not updated"));
                RequestProvider.redirect(String.format(RedirectCommand.PUBLISHER_PAGE, REDIRECT_PUBLISHER_ID + publisher.getId()), request, response);
            } else {
                logger.info(logMesBuilder.build("The entered data is invalid. Publisher was not added"));
                RequestProvider.redirect(String.format(RedirectCommand.PUBLISHER_PAGE, ""), request, response);
            }
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error adding/updating publisher data"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }

    private List<String> errorMessageBuilder(PublisherException exception) {
        List<String> messages = new ArrayList<>();
        for (PublisherException e : exception.getExceptions()) {
            messages.add(exceptionMessages.get(e.getClass().getSimpleName()));
        }
        return messages;
    }
}