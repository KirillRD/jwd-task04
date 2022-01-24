package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.command.util.Util;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.TypeService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;

public class DeleteType implements Command {
    private static final Logger logger = Logger.getLogger(DeleteType.class.getName());

    private static final String TYPE_ID = "type_id";
    private static final String MESSAGE = "message";
    private static final String ERROR_DELETE_TYPE = "error-delete-type";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogMessageBuilder logMesBuilder = new LogMessageBuilder(request);
        logger.info(logMesBuilder.build("Type delete started"));

        TypeService typeService = ServiceProvider.getInstance().getTypeService();
        try {
            int typeID;
            if (Util.isID(request.getParameter(TYPE_ID))) {
                typeID = Integer.parseInt(request.getParameter(TYPE_ID));
            } else {
                logger.error(logMesBuilder.build("Invalid page attributes. Type was not deleted"));
                RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            if (!typeService.deleteType(typeID)) {
                HttpSession session = request.getSession();
                session.setAttribute(MESSAGE, ERROR_DELETE_TYPE);
                logger.info(logMesBuilder.build("Type was not deleted. The book of this type already exists"));
            } else {
                logger.info(logMesBuilder.build("Type delete completed"));
            }

            RequestProvider.redirect(String.format(RedirectCommand.TYPE_PAGE, ""), request, response);
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error deleting type data"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
