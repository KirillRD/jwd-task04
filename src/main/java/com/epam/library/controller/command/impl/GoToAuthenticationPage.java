package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.PagePath;
import com.epam.library.controller.util.LogMessageBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Command to go to authentication page
 */
public class GoToAuthenticationPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToAuthenticationPage.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Go to authentication page"));

        RequestManager.forward(PagePath.AUTHENTICATION_PAGE, request, response);
    }
}
