package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.entity.Instance;
import com.epam.library.service.InstanceService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ValidationException;
import com.epam.library.service.exception.validation.InstanceValidationException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.validation.instance.*;
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
 * Command to add/edit book instance
 */
public class AddEditInstance implements Command {
    private static final Logger logger = Logger.getLogger(AddEditInstance.class.getName());

    private static final String BOOK_ID = "book_id";
    private static final String INSTANCE_ID = "instance_id";
    private static final String INSTANCE = "instance";

    private static final String NUMBER = "number";
    private static final String HALL = "hall";
    private static final String RECEIVED_DATE = "received_date";
    private static final String WRITE_OFF_DATE = "write_off_date";

    private static final String REDIRECT_INSTANCE_ID = "&instance_id=";
    private static final String MESSAGES = "messages";
    private static final Map<String, String> exceptionMessages = Map.ofEntries(
            Map.entry(EmptyHallException.class.getSimpleName(), "error-empty-hall"),
            Map.entry(EmptyNumberException.class.getSimpleName(), "error-empty-number"),
            Map.entry(EmptyReceivedDateException.class.getSimpleName(), "error-empty-received-date"),
            Map.entry(ExistNumberException.class.getSimpleName(), "error-exist-number"),
            Map.entry(InvalidHallFormatException.class.getSimpleName(), "error-hall-format"),
            Map.entry(InvalidLengthNumberException.class.getSimpleName(), "error-length-number"),
            Map.entry(InvalidReceivedDateFormatException.class.getSimpleName(), "error-received-date-format"),
            Map.entry(InvalidWriteOffDateFormatException.class.getSimpleName(), "error-write-off-date-format"),
            Map.entry(InvalidWriteOffDateException.class.getSimpleName(), "error-write-off-date")
    );

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);

        InstanceService instanceService = ServiceProvider.getInstance().getInstanceService();
        Instance instance = new Instance();
        if (Util.isID(request.getParameter(INSTANCE_ID))) {
            instance.setId(Integer.parseInt(request.getParameter(INSTANCE_ID)));
            logger.info(LogMessageBuilder.message(logMessage, "Instance update started"));
        } else {
            logger.info(LogMessageBuilder.message(logMessage, "Instance add started"));
        }

        int bookID;
        if (Util.isID(request.getParameter(BOOK_ID))) {
            bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        } else {
            logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Instance was not added/updated"));
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
            return;
        }

        instance.setBookID(bookID);
        instance.setNumber(request.getParameter(NUMBER));
        instance.setHallID(request.getParameter(HALL));
        instance.setReceivedDate(request.getParameter(RECEIVED_DATE));
        instance.setWriteOffDate(request.getParameter(WRITE_OFF_DATE));
        try {
            if (instance.getId() != 0) {
                instanceService.updateInstance(instance);
                logger.info(LogMessageBuilder.message(logMessage, "Instance update completed"));
            } else {
                instanceService.addInstance(instance);
                logger.info(LogMessageBuilder.message(logMessage, "Instance add completed"));
            }

            RequestManager.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID), request, response);
        } catch (InstanceValidationException e) {
            HttpSession session = request.getSession();
            session.setAttribute(INSTANCE, instance);
            session.setAttribute(MESSAGES, errorMessageBuilder(e));
            if (instance.getId() != 0) {
                logger.info(LogMessageBuilder.message(logMessage, "The entered data is invalid. Instance was not updated"));
                RequestManager.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID + REDIRECT_INSTANCE_ID + instance.getId()), request, response);
            } else {
                logger.info(LogMessageBuilder.message(logMessage, "The entered data is invalid. Instance was not added"));
                RequestManager.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID), request, response);
            }
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error adding/updating instance data"), e);
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }

    private List<String> errorMessageBuilder(InstanceValidationException exception) {
        List<String> messages = new ArrayList<>();
        for (ValidationException e : exception.getExceptions()) {
            messages.add(exceptionMessages.get(e.getClass().getSimpleName()));
        }
        return messages;
    }
}
