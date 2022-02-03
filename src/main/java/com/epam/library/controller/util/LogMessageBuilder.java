package com.epam.library.controller.util;

import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.user.SessionUser;
import jakarta.servlet.http.HttpServletRequest;

public class LogMessageBuilder {

    private static final String VERTICAL_BAR = "|";
    private static final String QUESTION = "?";

    public LogMessageBuilder() {}

    public static String build(HttpServletRequest request) {
        SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
        String userID = sessionUser != null ? String.valueOf(sessionUser.getId()) : "";
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String query = request.getQueryString() != null ? QUESTION + request.getQueryString() : "";
        return userID + VERTICAL_BAR + VERTICAL_BAR + method + VERTICAL_BAR + uri + query;
    }

    public static String message (String logMessage, String message) {
        int index = logMessage.indexOf(VERTICAL_BAR) + 1;
        return logMessage.substring(0, index) + message + logMessage.substring(index);
    }
}
