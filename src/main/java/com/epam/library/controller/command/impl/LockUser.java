package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

public class LockUser implements Command {
    private static final Logger logger = Logger.getLogger(LockUser.class.getName());

    private static final String USER_ID = "user_id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "User lock started"));

        UserService userService = ServiceProvider.getInstance().getUserService();
        try {
            int userID;
            if (Util.isID(request.getParameter(USER_ID))) {
                userID = Integer.parseInt(request.getParameter(USER_ID));
            } else {
                logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. User lock is failed"));
                RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            userService.lockUser(userID);
            logger.info(LogMessageBuilder.message(logMessage, "User lock completed"));
            RequestProvider.redirect(RedirectCommand.USER_LIST_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error deleting type data"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
