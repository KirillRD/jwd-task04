package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.User;
import com.epam.library.entity.user.Role;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToEditUserPage implements Command {

    private static final String USER_ID = "user_id";
    private static final String READER_ID = "reader_id";
    private static final String USER = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = ServiceProvider.getInstance().getUserService();

        try {
            int id;
            SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
            if (request.getParameter(USER_ID) != null && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
                id = Integer.parseInt(request.getParameter(USER_ID));
            } else if (request.getParameter(READER_ID) != null && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
                id = Integer.parseInt(request.getParameter(READER_ID));
            } else {
                id = sessionUser.getId();
            }
            User user = userService.getUser(id);
            request.setAttribute(USER, user);
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.forward(PagePath.EDIT_USER_PAGE, request, response);
    }
}
