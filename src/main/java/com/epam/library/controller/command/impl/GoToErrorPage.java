package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.PagePath;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Set;

/**
 * Command to go to error page
 */
public class GoToErrorPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToErrorPage.class.getName());

    private static final String ERROR = "error";
    private static final Set<String> errorMessages = Set.of(
            ErrorMessage.PAGE_NOT_FOUND,
            ErrorMessage.GENERAL_ERROR
    );

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Go to error page"));

        String error = request.getParameter(ERROR);
        if (error != null && errorMessages.contains(error)) {
            RequestManager.forward(PagePath.ERROR_PAGE, request, response);
        } else {
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
        }
    }
}
