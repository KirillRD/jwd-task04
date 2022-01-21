package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.session.SessionUserProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

public class LogOut implements Command {
    private static final Logger logger = Logger.getLogger(LogOut.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(logMessageBuilder("Log out started", request));
        SessionUserProvider.removeSessionUser(request);
        logger.info(logMessageBuilder("Log out completed", request));
        RequestProvider.redirect(RedirectCommand.MAIN_PAGE, request, response);
    }
}
