package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.util.LogMessageBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

public class GoToMainPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToMainPage.class.getName());
    private LogMessageBuilder logMesBuilder;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logMesBuilder = new LogMessageBuilder(request);
        logger.info(logMesBuilder.build("Go to main page"));

        RequestProvider.forward(PagePath.MAIN_PAGE, request, response);
    }
}
