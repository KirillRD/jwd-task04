package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.command.util.Util;
import com.epam.library.entity.User;
import com.epam.library.constant.UserListFilterName;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class UserListPage implements Command {
    private static final Logger logger = Logger.getLogger(UserListPage.class.getName());

    private static final String USER_LIST = "user_list";
    private static final String SELECTED = "selected";

    private String url;
    private static final String PAGE = "page";
    private static final String PAGES_COUNT = "pages_count";
    private static final String URL = "url";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogMessageBuilder logMesBuilder = new LogMessageBuilder(request);
        logger.info(logMesBuilder.build("User list build started"));

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
            int page;
            if (Util.isID(request.getParameter(PAGE))) {
                page = Integer.parseInt(request.getParameter(PAGE));
            } else {
                url = request.getQueryString();
                page = 1;
            }
            request.setAttribute(PAGE, page);
            request.setAttribute(URL, url);

            users = userService.getUsersByFilter(filters, page);
            request.setAttribute(USER_LIST, users);

            int pagesCount = userService.getPagesCount();
            request.setAttribute(PAGES_COUNT, pagesCount);

            logger.info(logMesBuilder.build("User list building completed"));

            RequestProvider.forward(PagePath.USER_LIST_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error getting data for user list"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
