package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.entity.User;
import com.epam.library.entity.user.UserListFilterName;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserListPage implements Command {

    private static final String USER_LIST = "user_list";
    private static final String SELECTED = "selected";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = ServiceProvider.getInstance().getUserService();

        Map<String, Object> filters = new LinkedHashMap<>();
        if (isValidInsertFilter(UserListFilterName.LAST_NAME, request)) {
            filters.put(UserListFilterName.LAST_NAME, request.getParameter(UserListFilterName.LAST_NAME));
            request.setAttribute(UserListFilterName.LAST_NAME, request.getParameter(UserListFilterName.LAST_NAME));
        }
        if (isValidInsertFilter(UserListFilterName.EMAIL, request)) {
            filters.put(UserListFilterName.EMAIL, request.getParameter(UserListFilterName.EMAIL));
            request.setAttribute(UserListFilterName.EMAIL, request.getParameter(UserListFilterName.EMAIL));
        }
        if (isValidInsertFilter(UserListFilterName.NICKNAME, request)) {
            filters.put(UserListFilterName.NICKNAME, request.getParameter(UserListFilterName.NICKNAME));
            request.setAttribute(UserListFilterName.NICKNAME, request.getParameter(UserListFilterName.NICKNAME));
        }
        if (isValidInsertFilter(UserListFilterName.SORT, request)) {
            filters.put(UserListFilterName.SORT, request.getParameter(UserListFilterName.SORT));
            request.setAttribute(request.getParameter(UserListFilterName.SORT), SELECTED);
        } else {
            filters.put(UserListFilterName.SORT, UserListFilterName.LAST_NAME_ASCENDING);
        }

        List<User> users;
        try {
            users = userService.getUsersByFilter(filters);
            request.setAttribute(USER_LIST, users.toArray());
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.forward(PagePath.USER_LIST_PAGE, request, response);
    }

    private boolean isValidInsertFilter(String nameFilter, HttpServletRequest request) {
        String value = request.getParameter(nameFilter);
        if (value == null || value.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
