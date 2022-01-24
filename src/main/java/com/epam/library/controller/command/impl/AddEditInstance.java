package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.command.util.Util;
import com.epam.library.entity.instance.InstanceInfo;
import com.epam.library.service.InstanceService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.InstanceException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.instance.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            Map.entry(InvalidWriteOffDateFormatException.class.getSimpleName(), "error-write-off-date-format")
    );

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogMessageBuilder logMesBuilder = new LogMessageBuilder(request);

        InstanceService instanceService = ServiceProvider.getInstance().getInstanceService();
        InstanceInfo instance = new InstanceInfo();
        if (Util.isID(request.getParameter(INSTANCE_ID))) {
            instance.setId(Integer.parseInt(request.getParameter(INSTANCE_ID)));
            logger.info(logMesBuilder.build("Instance update started"));
        } else {
            logger.info(logMesBuilder.build("Instance add started"));
        }

        int bookID;
        if (Util.isID(request.getParameter(BOOK_ID))) {
            bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        } else {
            logger.error(logMesBuilder.build("Invalid page attributes. Instance was not added/updated"));
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
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
                logger.info(logMesBuilder.build("Instance update completed"));
            } else {
                instanceService.addInstance(instance);
                logger.info(logMesBuilder.build("Instance add completed"));
            }

            RequestProvider.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID), request, response);
        } catch (InstanceException e) {
            HttpSession session = request.getSession();
            session.setAttribute(INSTANCE, instance);
            session.setAttribute(MESSAGES, errorMessageBuilder(e));
            if (instance.getId() != 0) {
                logger.info(logMesBuilder.build("The entered data is invalid. Instance was not updated"));
                RequestProvider.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID + REDIRECT_INSTANCE_ID + instance.getId()), request, response);
            } else {
                logger.info(logMesBuilder.build("The entered data is invalid. Instance was not added"));
                RequestProvider.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID), request, response);
            }
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error adding/updating instance data"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }

    private List<String> errorMessageBuilder(InstanceException exception) {
        List<String> messages = new ArrayList<>();
        for (InstanceException e : exception.getExceptions()) {
            messages.add(exceptionMessages.get(e.getClass().getSimpleName()));
        }
        return messages;
    }
}
