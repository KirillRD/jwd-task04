package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class Authentication implements Command {

    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";

    private static final String MESSAGE = "message";
    private static final String ERROR_AUTHENTICATION = "error-authentication";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        UserService userService = ServiceProvider.getInstance().getUserService();

        try {
            Integer userID = userService.authentication(email, password);
            if (userID != null) {
                SessionUser sessionUser = userService.getSessionUser(userID);
                SessionUserProvider.setSessionUser(request, sessionUser);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute(EMAIL, email);
                session.setAttribute(MESSAGE, ERROR_AUTHENTICATION);
                RequestProvider.redirect(RedirectCommand.AUTHENTICATION_PAGE, request, response);
                return;
            }

            RequestProvider.redirect(RedirectCommand.MAIN_PAGE, request, response);
        } catch (ServiceException e) {
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}