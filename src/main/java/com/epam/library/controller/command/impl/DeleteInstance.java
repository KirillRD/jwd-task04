package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.service.InstanceService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteInstance implements Command {

    private static final String BOOK_ID = "book_id";
    private static final String INSTANCE_ID = "instance_id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InstanceService instanceService = ServiceProvider.getInstance().getInstanceService();
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        try {

            if (request.getParameter(INSTANCE_ID) != null) {
                int instanceID = Integer.parseInt(request.getParameter(INSTANCE_ID));
                instanceService.deleteInstance(instanceID);
            }

        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID), request, response);
    }
}
