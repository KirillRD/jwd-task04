package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.entity.book.dictionary.Type;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.TypeService;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.TypeException;
import com.epam.library.service.exception.type.EmptyTypeNameException;
import com.epam.library.service.exception.type.ExistTypeException;
import com.epam.library.service.exception.type.InvalidLengthTypeNameException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddEditType implements Command {
    private static final Logger logger = Logger.getLogger(AddEditType.class.getName());

    private static final String TYPE_ID = "type_id";
    private static final String TYPE = "type";

    private static final String NAME = "name";

    private static final String REDIRECT_TYPE_ID = "&type_id=";
    private static final String MESSAGES = "messages";
    private static final Map<String, String> exceptionMessages = Map.ofEntries(
            Map.entry(EmptyTypeNameException.class.getSimpleName(), "error-empty-type-name"),
            Map.entry(ExistTypeException.class.getSimpleName(), "error-exist-type"),
            Map.entry(InvalidLengthTypeNameException.class.getSimpleName(), "error-length-type-name")
    );

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);

        TypeService typeService = ServiceProvider.getInstance().getTypeService();
        Type type = new Type();
        if (Util.isID(request.getParameter(TYPE_ID))) {
            type.setId(Integer.parseInt(request.getParameter(TYPE_ID)));
            logger.info(LogMessageBuilder.message(logMessage, "Type update started"));
        } else {
            logger.info(LogMessageBuilder.message(logMessage, "Type add started"));
        }

        type.setName(request.getParameter(NAME));
        try {
            if (type.getId() != 0) {
                typeService.updateType(type);
                logger.info(LogMessageBuilder.message(logMessage, "Type update completed"));
            } else {
                typeService.addType(type);
                logger.info(LogMessageBuilder.message(logMessage, "Type add completed"));
            }

            RequestProvider.redirect(String.format(RedirectCommand.TYPE_PAGE, ""), request, response);
        } catch (TypeException e) {
            HttpSession session = request.getSession();
            session.setAttribute(TYPE, type);
            session.setAttribute(MESSAGES, errorMessageBuilder(e));
            if (type.getId() != 0) {
                logger.info(LogMessageBuilder.message(logMessage, "The entered data is invalid. Type was not updated"));
                RequestProvider.redirect(String.format(RedirectCommand.TYPE_PAGE, REDIRECT_TYPE_ID + type.getId()), request, response);
            } else {
                logger.info(LogMessageBuilder.message(logMessage, "The entered data is invalid. Type was not added"));
                RequestProvider.redirect(String.format(RedirectCommand.TYPE_PAGE, ""), request, response);
            }
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error adding/updating type data"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }

    private List<String> errorMessageBuilder(TypeException exception) {
        List<String> messages = new ArrayList<>();
        for (TypeException e : exception.getExceptions()) {
            messages.add(exceptionMessages.get(e.getClass().getSimpleName()));
        }
        return messages;
    }
}
