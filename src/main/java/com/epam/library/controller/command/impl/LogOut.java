package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.session.SessionUserProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Command to log out user
 */
public class LogOut implements Command {
    private static final Logger logger = Logger.getLogger(LogOut.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Log out started"));

        SessionUserProvider.removeSessionUser(request);
        logger.info(LogMessageBuilder.message(logMessage, "Log out completed"));
        RequestManager.redirect(RedirectCommand.MAIN_PAGE, request, response);
    }
}
