package com.epam.library.controller.filter;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.constant.CommandName;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.user.Role;
import com.epam.library.entity.user.SessionUser;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Set;

public class RoleFilter implements Filter {
    private static final Logger logger = Logger.getLogger(RoleFilter.class.getName());
    private LogMessageBuilder logMesBuilder;

    private static final String COMMAND = "command";

    private static final Set<String> userCommands = Set.of(
            CommandName.LOG_OUT.getCommandName(),
            CommandName.GO_TO_USER_PAGE.getCommandName(),
            CommandName.GO_TO_EDIT_USER_PAGE.getCommandName(),
            CommandName.EDIT_USER.getCommandName()
    );
    private static final Set<String> commonCommands = Set.of(
            CommandName.GO_TO_MAIN_PAGE.getCommandName(),
            CommandName.CHANGE_LOCALE.getCommandName(),
            CommandName.GO_TO_BOOK_PAGE.getCommandName(),
            CommandName.BOOK_CATALOG_PAGE.getCommandName(),
            CommandName.GO_TO_REGISTRATION_PAGE.getCommandName(),
            CommandName.GO_TO_AUTHENTICATION_PAGE.getCommandName(),
            CommandName.REGISTRATION.getCommandName(),
            CommandName.AUTHENTICATION.getCommandName(),
            CommandName.GO_TO_NEW_BOOK_CATALOG_PAGE.getCommandName(),
            CommandName.GO_TO_POPULAR_BOOK_CATALOG_PAGE.getCommandName(),
            CommandName.GO_TO_ERROR_PAGE.getCommandName()
    );
    private static final Set<String> readerCommands = Set.of(
            CommandName.GO_TO_RESERVATION_PAGE.getCommandName(),
            CommandName.ADD_RESERVATION.getCommandName(),
            CommandName.ADD_REVIEW.getCommandName(),
            CommandName.DELETE_RESERVATION.getCommandName()
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        logMesBuilder = new LogMessageBuilder(request);

        SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
        String command = request.getParameter(COMMAND);
        if (CommandName.containsCommandName(command)) {
            if (sessionUser == null) {
                if (!commonCommands.contains(command)) {
                    if (readerCommands.contains(command) || userCommands.contains(command)) {
                        logger.info(logMesBuilder.build("Unauthorized user trying to execute reader command"));
                        RequestProvider.redirect(RedirectCommand.AUTHENTICATION_PAGE, request, response);
                    } else {
                        logger.warn(logMesBuilder.build("Unauthorized user trying to admin/librarian command"));
                        RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                    }
                    return;
                }
            } else if (sessionUser.getRole() == Role.READER) {
                if (!(commonCommands.contains(command) || readerCommands.contains(command) || userCommands.contains(command))) {
                    logger.warn(logMesBuilder.build("Reader trying to execute admin/librarian command"));
                    RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                    return;
                }
            } else if (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN) {
                if (readerCommands.contains(command)) {
                    logger.info(logMesBuilder.build("Admin/Librarian trying to execute reader command"));
                    RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
