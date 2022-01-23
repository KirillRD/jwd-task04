package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.command.util.Util;
import com.epam.library.service.InstanceService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;

public class DeleteInstance implements Command {
    private static final Logger logger = Logger.getLogger(DeleteInstance.class.getName());
    private LogMessageBuilder logMesBuilder;

    private static final String BOOK_ID = "book_id";
    private static final String INSTANCE_ID = "instance_id";
    private static final String MESSAGE = "message";
    private static final String ERROR_DELETE_INSTANCE = "error-delete-instance";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logMesBuilder = new LogMessageBuilder(request);
        logger.info(logMesBuilder.build("Instance delete started"));

        InstanceService instanceService = ServiceProvider.getInstance().getInstanceService();

        int bookID;
        if (Util.isID(request.getParameter(BOOK_ID))) {
            bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        } else {
            logger.error(logMesBuilder.build("Invalid page attributes. Instance was not deleted"));
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
            return;
        }

        try {
            int instanceID = Integer.parseInt(request.getParameter(INSTANCE_ID));
            if (!instanceService.deleteInstance(instanceID)) {
                HttpSession session = request.getSession();
                session.setAttribute(MESSAGE, ERROR_DELETE_INSTANCE);
                logger.info(logMesBuilder.build("Instance was not deleted. Instance has an issue history or a reservation history"));
            } else {
                logger.info(logMesBuilder.build("Instance delete completed"));
            }

            RequestProvider.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID), request, response);
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error deleting instance data"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
