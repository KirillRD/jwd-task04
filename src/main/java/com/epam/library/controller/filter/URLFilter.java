package com.epam.library.controller.filter;

import com.epam.library.controller.command.constant.CommandName;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class URLFilter implements Filter {

    private static final String GET = "GET";
    private static final String COMMAND = "command";

    private static final String LOCALE = "locale";
    private static final String EN = "en";
    private static final String URL = "url";
    private static final String QUESTION = "?";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        if (session.getAttribute(LOCALE) == null) {
            session.setAttribute(LOCALE, EN);
        }

        String method = request.getMethod();
        String command = request.getParameter(COMMAND);
        if (GET.equals(method) && !CommandName.CHANGE_LOCALE.getCommandName().equals(command)) {
            String uri = request.getRequestURI();
            String query = request.getQueryString();
            String url = uri + (query != null ? (QUESTION + query) : "");
            session.setAttribute(URL, url);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
