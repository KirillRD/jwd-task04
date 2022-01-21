package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ChangeLocale implements Command {
    private static final Logger logger = Logger.getLogger(ChangeLocale.class.getName());

    private static final String LOCALE = "locale";
    private static final String URL = "url";
    private static final String EN = "en";
    private static final String RU = "ru";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(logMessageBuilder("Locale change started", request));
        if (request.getParameter(LOCALE) != null && (EN.equals(request.getParameter(LOCALE)) || RU.equals(request.getParameter(LOCALE)))) {
            HttpSession session = request.getSession();
            session.setAttribute(LOCALE, request.getParameter(LOCALE));
            String url = (String) session.getAttribute(URL);
            logger.info(logMessageBuilder("Locale change completed", request));
            response.sendRedirect(url);
        } else {
            logger.error(logMessageBuilder("Invalid page attributes. Locale was not changed", request));
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
        }
    }
}
