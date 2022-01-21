package com.epam.library.controller.command;

import com.epam.library.controller.session.SessionUserProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface Command {
    String LOG_MESSAGE_FORMAT = "%1$s| %2$s |%3$s|%4$s%5$s";
    String QUESTION = "?";

    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    default String logMessageBuilder(String message, HttpServletRequest request) {
        return String.format(LOG_MESSAGE_FORMAT,
                (SessionUserProvider.getSessionUser(request) != null ? SessionUserProvider.getSessionUser(request).getId() : ""),
                message,
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString() != null ? QUESTION.concat(request.getQueryString()) : ""
        );
    }
}
