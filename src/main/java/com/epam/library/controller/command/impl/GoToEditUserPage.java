package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.PagePath;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.User;
import com.epam.library.entity.user.constant.Role;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;

public class GoToEditUserPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToEditUserPage.class.getName());

    private static final String USER_ID = "user_id";
    private static final String READER_ID = "reader_id";
    private static final String USER = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Going to page for updating user started"));

        UserService userService = ServiceProvider.getInstance().getUserService();

        try {
            int userID;
            SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
            if (Util.isID(request.getParameter(USER_ID)) && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
                logger.info(LogMessageBuilder.message(logMessage, "Admin/Librarian goes to page for updating user"));
                userID = Integer.parseInt(request.getParameter(USER_ID));
            } else if (Util.isID(request.getParameter(READER_ID)) && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
                logger.info(LogMessageBuilder.message(logMessage, "Admin/Librarian goes to page for updating user"));
                userID = Integer.parseInt(request.getParameter(READER_ID));
            } else {
                logger.info(LogMessageBuilder.message(logMessage, "User goes to page for updating self"));
                userID = sessionUser.getId();
            }
            User user = userService.getUser(userID);
            if (user == null) {
                logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Going to page for updating user is failed"));
                RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            HttpSession session = request.getSession();
            if (session.getAttribute(USER) == null) {
                session.setAttribute(USER, user);
            }

            logger.info(LogMessageBuilder.message(logMessage, "Going to page for updating user was completed"));
            RequestProvider.forward(PagePath.EDIT_USER_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error in data while going to page for updating user"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
