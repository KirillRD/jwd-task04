package com.epam.library.controller.filter;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.constant.CommandName;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.user.Role;
import com.epam.library.entity.user.SessionUser;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RoleFilter implements Filter {

    private static final String COMMAND = "command";

    private final Set<String> readerCommands = new HashSet<>();
    private final Set<String> commonCommands = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commonCommands.add(CommandName.GO_TO_MAIN_PAGE.getCommandName());
        commonCommands.add(CommandName.CHANGE_LOCALE.getCommandName());
        commonCommands.add(CommandName.GO_TO_BOOK_PAGE.getCommandName());
        commonCommands.add(CommandName.BOOK_CATALOG_PAGE.getCommandName());
        commonCommands.add(CommandName.GO_TO_REGISTRATION_PAGE.getCommandName());
        commonCommands.add(CommandName.GO_TO_AUTHENTICATION_PAGE.getCommandName());
        commonCommands.add(CommandName.REGISTRATION.getCommandName());
        commonCommands.add(CommandName.AUTHENTICATION.getCommandName());
        commonCommands.add(CommandName.GO_TO_NEW_BOOK_CATALOG_PAGE.getCommandName());
        commonCommands.add(CommandName.GO_TO_POPULAR_BOOK_CATALOG_PAGE.getCommandName());

        readerCommands.add(CommandName.GO_TO_RESERVATION_PAGE.getCommandName());
        readerCommands.add(CommandName.LOG_OUT.getCommandName());
        readerCommands.add(CommandName.ADD_RESERVATION.getCommandName());
        readerCommands.add(CommandName.GO_TO_USER_PAGE.getCommandName());
        readerCommands.add(CommandName.GO_TO_EDIT_USER_PAGE.getCommandName());
        readerCommands.add(CommandName.EDIT_USER.getCommandName());
        readerCommands.add(CommandName.ADD_REVIEW.getCommandName());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
        String command = request.getParameter(COMMAND);
        if (command != null) {
            if (sessionUser == null) {
                if (!commonCommands.contains(command)) {
                    if (readerCommands.contains(command)) {
                        RequestProvider.forward(PagePath.AUTHENTICATION_PAGE, request, response);
                    } else {
                        request.setAttribute(ErrorMessage.ERROR, ErrorMessage.PAGE_NOT_FOUND);
                        RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
                    }
                }
            } else if (sessionUser.getRole() == Role.READER) {
                if (!(commonCommands.contains(command) || readerCommands.contains(command))) {
                    request.setAttribute(ErrorMessage.ERROR, ErrorMessage.PAGE_NOT_FOUND);
                    RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
