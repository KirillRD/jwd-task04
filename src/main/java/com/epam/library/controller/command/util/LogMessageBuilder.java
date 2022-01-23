package com.epam.library.controller.command.util;

import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.user.SessionUser;
import jakarta.servlet.http.HttpServletRequest;

public class LogMessageBuilder {

    private static final String LOG_MESSAGE_FORMAT = "%1$s| %2$s |%3$s|%4$s%5$s";
    private static final String QUESTION = "?";

    private final String userID;
    private final String method;
    private final String uri;
    private final String query;

    public LogMessageBuilder(HttpServletRequest request) {
        SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
        userID = sessionUser != null ? String.valueOf(sessionUser.getId()) : "";
        method = request.getMethod();
        uri = request.getRequestURI();
        query = request.getQueryString() != null ? QUESTION.concat(request.getQueryString()) : "";
    }

    public String build(String message) {
        return String.format(LOG_MESSAGE_FORMAT, userID, message, method, uri, query);
    }
}
