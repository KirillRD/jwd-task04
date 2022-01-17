package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.entity.Instance;
import com.epam.library.service.InstanceService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;

public class AddEditInstance implements Command {

    private static final String BOOK_ID = "book_id";
    private static final String INSTANCE_ID = "instance_id";
    private static final String INSTANCE = "instance";

    private static final String NUMBER = "number";
    private static final String HALL = "hall";
    private static final String RECEIVED_DATE = "received_date";
    private static final String WRITE_OFF_DATE = "write_off_date";

    private static final String REDIRECT_INSTANCE_ID = "&instance_id=";
    private static final String MESSAGE = "message";
    private static final String ERROR_ADD_EDIT_INSTANCE = "error-add-edit-instance";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InstanceService instanceService = ServiceProvider.getInstance().getInstanceService();
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        try {

            Instance instance = new Instance();
            if (request.getParameter(INSTANCE_ID) != null) {
                instance.setId(Integer.parseInt(request.getParameter(INSTANCE_ID)));
            }
            instance.setBookID(bookID);
            instance.setNumber(request.getParameter(NUMBER));
            instance.setHallID(Integer.parseInt(request.getParameter(HALL)));
            Date date = Date.valueOf(request.getParameter(RECEIVED_DATE));
            instance.setReceivedDate(date);
            if (!request.getParameter(WRITE_OFF_DATE).isEmpty()) {
                date = Date.valueOf(request.getParameter(WRITE_OFF_DATE));
                instance.setWriteOffDate(date);
            }

            if (instance.getId() != 0) {
                if (!instanceService.updateInstance(instance)) {
                    HttpSession session = request.getSession();
                    session.setAttribute(INSTANCE, instance);
                    session.setAttribute(MESSAGE, ERROR_ADD_EDIT_INSTANCE);
                    RequestProvider.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID + REDIRECT_INSTANCE_ID + instance.getId()), request, response);
                    return;
                }
            } else {
                if (!instanceService.addInstance(instance)) {
                    HttpSession session = request.getSession();
                    session.setAttribute(INSTANCE, instance);
                    session.setAttribute(MESSAGE, ERROR_ADD_EDIT_INSTANCE);
                }
            }

            RequestProvider.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID), request, response);
        } catch (ServiceException e) {
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
