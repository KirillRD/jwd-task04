package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

public class GoToRegistrationPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToRegistrationPage.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(logMessageBuilder("Go to registration page", request));
        RequestProvider.forward(PagePath.REGISTRATION_PAGE, request, response);
    }
}
