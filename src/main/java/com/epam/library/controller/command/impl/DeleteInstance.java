package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.service.InstanceService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class DeleteInstance implements Command {

    private static final String BOOK_ID = "book_id";
    private static final String INSTANCE_ID = "instance_id";
    private static final String MESSAGE = "message";
    private static final String ERROR_DELETE_INSTANCE = "error-delete-instance";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InstanceService instanceService = ServiceProvider.getInstance().getInstanceService();
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));

        try {
            int instanceID = Integer.parseInt(request.getParameter(INSTANCE_ID));
            if (!instanceService.deleteInstance(instanceID)) {
                HttpSession session = request.getSession();
                session.setAttribute(MESSAGE, ERROR_DELETE_INSTANCE);
            }

            RequestProvider.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID), request, response);
        } catch (ServiceException e) {
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
