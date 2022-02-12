package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.TypeService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Command to delete book type
 */
public class DeleteType implements Command {
    private static final Logger logger = Logger.getLogger(DeleteType.class.getName());

    private static final String TYPE_ID = "type_id";
    private static final String MESSAGE = "message";
    private static final String ERROR_DELETE_TYPE = "error-delete-type";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Type delete started"));

        TypeService typeService = ServiceProvider.getInstance().getTypeService();
        try {
            int typeID;
            if (Util.isID(request.getParameter(TYPE_ID))) {
                typeID = Integer.parseInt(request.getParameter(TYPE_ID));
            } else {
                logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Type was not deleted"));
                RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            if (!typeService.deleteType(typeID)) {
                HttpSession session = request.getSession();
                session.setAttribute(MESSAGE, ERROR_DELETE_TYPE);
                logger.info(LogMessageBuilder.message(logMessage, "Type was not deleted. The book of this type already exists"));
            } else {
                logger.info(LogMessageBuilder.message(logMessage, "Type delete completed"));
            }

            RequestManager.redirect(String.format(RedirectCommand.TYPE_PAGE, ""), request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error deleting type data"), e);
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
