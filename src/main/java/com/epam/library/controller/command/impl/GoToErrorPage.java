package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Set;

public class GoToErrorPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToErrorPage.class.getName());

    private static final String ERROR = "error";
    private static final Set<String> errorMessages = Set.of(
            ErrorMessage.PAGE_NOT_FOUND,
            ErrorMessage.GENERAL_ERROR
    );

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(logMessageBuilder("Go to error page", request));
        String error = request.getParameter(ERROR);
        if (error != null && errorMessages.contains(error)) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        } else {
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
        }
    }
}
