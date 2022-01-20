package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddEditInstance implements Command {

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
        InstanceService instanceService = ServiceProvider.getInstance().getInstanceService();
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        InstanceInfo instance = new InstanceInfo();

        if (request.getParameter(INSTANCE_ID) != null) {
            instance.setId(Integer.parseInt(request.getParameter(INSTANCE_ID)));
        }
        instance.setBookID(bookID);
        instance.setNumber(request.getParameter(NUMBER));
        instance.setHallID(request.getParameter(HALL));
        instance.setReceivedDate(request.getParameter(RECEIVED_DATE));
        instance.setWriteOffDate(request.getParameter(WRITE_OFF_DATE));
        try {
            if (instance.getId() != 0) {
                instanceService.updateInstance(instance);
            } else {
                instanceService.addInstance(instance);
            }

            RequestProvider.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID), request, response);
        } catch (InstanceException e) {
            HttpSession session = request.getSession();
            session.setAttribute(INSTANCE, instance);
            session.setAttribute(MESSAGES, errorMessageBuilder(e));
            if (instance.getId() != 0) {
                RequestProvider.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID + REDIRECT_INSTANCE_ID + instance.getId()), request, response);
            } else {
                RequestProvider.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID), request, response);
            }
        } catch (ServiceException e) {
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
