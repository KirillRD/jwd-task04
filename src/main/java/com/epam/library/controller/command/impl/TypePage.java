package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.PagePath;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.entity.book.dictionary.Type;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.TypeService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Command to go to the book's type page and crossing type editing mode
 */
public class TypePage implements Command {
    private static final Logger logger = Logger.getLogger(TypePage.class.getName());

    private static final String TYPES = "types";
    private static final String TYPE_ID = "type_id";
    private static final String TYPE = "type";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Type list build started"));

        TypeService typeService = ServiceProvider.getInstance().getTypeService();

        try {
            List<Type> types = typeService.getTypesList();
            request.setAttribute(TYPES, types);
            if (Util.isID(request.getParameter(TYPE_ID))) {
                int typeID = Integer.parseInt(request.getParameter(TYPE_ID));
                Type type = typeService.getType(typeID);
                if (type == null) {
                    logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Type was not found"));
                    RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                    return;
                }
                HttpSession session = request.getSession();
                if (session.getAttribute(TYPE) == null) {
                    session.setAttribute(TYPE, type);
                }
            }
            logger.info(LogMessageBuilder.message(logMessage, "Type list building completed"));

            RequestManager.forward(PagePath.TYPE_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error getting data for type list"), e);
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
