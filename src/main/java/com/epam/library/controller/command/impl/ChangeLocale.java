package com.epam.library.controller.command.impl;

import com.epam.library.controller.command.Command;
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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = (String) session.getAttribute(URL);
        String locale = request.getParameter(LOCALE);
        session.setAttribute(LOCALE, locale);
        response.sendRedirect(url);
    }
}
