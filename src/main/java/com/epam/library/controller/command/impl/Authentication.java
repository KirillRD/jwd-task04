package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Command to authentication user
 */
public class Authentication implements Command {
    private static final Logger logger = Logger.getLogger(Authentication.class.getName());

    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";

    private static final String MESSAGE = "message";
    private static final String ERROR_AUTHENTICATION = "error-authentication";
    private static final String ERROR_LOCK = "error-lock";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Authentication started"));

        UserService userService = ServiceProvider.getInstance().getUserService();
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        try {
            Integer userID = userService.authentication(email, password);
            if (userID != null) {
                if (userService.userIsLock(userID)) {
                    HttpSession session = request.getSession();
                    session.setAttribute(EMAIL, email);
                    session.setAttribute(MESSAGE, ERROR_LOCK);
                    logger.info(LogMessageBuilder.message(logMessage, "Authentication was not completed. User is locked"));
                    RequestManager.redirect(RedirectCommand.AUTHENTICATION_PAGE, request, response);
                    return;
                }
                SessionUser sessionUser = userService.getSessionUser(userID);
                SessionUserProvider.setSessionUser(request, sessionUser);
                logger.info(LogMessageBuilder.message(LogMessageBuilder.build(request), "Authentication completed"));
            } else {
                HttpSession session = request.getSession();
                session.setAttribute(EMAIL, email);
                session.setAttribute(MESSAGE, ERROR_AUTHENTICATION);
                logger.info(LogMessageBuilder.message(logMessage, "Authentication was not completed. Email or password entered incorrectly"));
                RequestManager.redirect(RedirectCommand.AUTHENTICATION_PAGE, request, response);
                return;
            }

            RequestManager.redirect(RedirectCommand.MAIN_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error data authentication"), e);
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
