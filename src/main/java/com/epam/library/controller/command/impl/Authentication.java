package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.User;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Authentication implements Command {

    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String ERROR = "error";
    private static final String INVALID_EMAIL_OR_PASSWORD = "Invalid email or password";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user;
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        UserService userService = ServiceProvider.getInstance().getUserService();

        try {
            user = userService.authentication(email, password);

            if (user != null) {
                SessionUser sessionUser = userService.getSessionUser(user.getId());
                SessionUserProvider.setSessionUser(request, sessionUser);
            } else {
                request.setAttribute(ERROR, INVALID_EMAIL_OR_PASSWORD);
                RequestProvider.forward(PagePath.AUTHENTICATION_PAGE, request, response);
            }
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.redirect(RedirectCommand.MAIN_PAGE, request, response);
    }
}
