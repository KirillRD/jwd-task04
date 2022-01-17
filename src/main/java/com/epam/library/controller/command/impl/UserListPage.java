package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.entity.User;
import com.epam.library.entity.user.UserListFilterName;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

public class UserListPage implements Command {

    private static final String USER_LIST = "user_list";
    private static final String SELECTED = "selected";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = ServiceProvider.getInstance().getUserService();

        Map<String, Object> filters = new LinkedHashMap<>();

        Set<String> filterNames = new HashSet<>(UserListFilterName.userListFilterName);
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        String sortValue = null;
        for (String filterName : requestParameterMap.keySet()) {
            if (filterNames.contains(filterName)) {
                for (String filterValue : requestParameterMap.get(filterName)) {
                    if (!filterValue.isEmpty()) {
                        if (filterName.equals(UserListFilterName.SORT)) {
                            if (UserListFilterName.userListFilterSortValues.contains(filterValue)) {
                                sortValue = filterValue;
                                request.setAttribute(filterValue, SELECTED);
                                filterNames.remove(filterName);
                            }
                        } else {
                            filters.put(filterName, filterValue);
                            request.setAttribute(filterName, filterValue);
                            filterNames.remove(filterName);
                        }
                    }
                }
            }
        }

        if (sortValue != null) {
            filters.put(UserListFilterName.SORT, sortValue);
        } else {
            filters.put(UserListFilterName.SORT, UserListFilterName.LAST_NAME_ASCENDING);
        }

        List<User> users;
        try {
            users = userService.getUsersByFilter(filters);
            request.setAttribute(USER_LIST, users);

            RequestProvider.forward(PagePath.USER_LIST_PAGE, request, response);
        } catch (ServiceException e) {
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
