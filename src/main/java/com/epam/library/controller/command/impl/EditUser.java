package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.User;
import com.epam.library.entity.user.Gender;
import com.epam.library.entity.user.Role;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.UpdateUserException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;

public class EditUser implements Command {

    private static final String USER_ID = "user_id";
    private static final String READER_ID = "reader_id";
    private static final String USER = "user";

    private static final String LAST_NAME = "last_name";
    private static final String FIRST_NAME = "first_name";
    private static final String FATHER_NAME = "father_name";
    private static final String BIRTHDAY = "birthday";
    private static final String GENDER = "gender";
    private static final String PASSPORT = "passport";

    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String NICKNAME = "nickname";

    private static final String CURRENT_PASSWORD = "current_password";
    private static final String NEW_PASSWORD = "new_password";
    private static final String REPEATED_NEW_PASSWORD = "repeated_new_password";

    private static final String ROLE = "role";

    private static final String REGEX_WHITESPACE_CHARACTERS = "\\s+";

    private static final String REDIRECT_USER_ID = "&user_id=";
    private static final String MESSAGE = "message";
    private static final String ERROR_EMAIL = "error-email";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = ServiceProvider.getInstance().getUserService();

        int userID;
        SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
        if (request.getParameter(USER_ID) != null && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
            userID = Integer.parseInt(request.getParameter(USER_ID));
        } else if (request.getParameter(READER_ID) != null && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
            userID = Integer.parseInt(request.getParameter(READER_ID));
        } else {
            userID = sessionUser.getId();
        }

        User user = new User();
        user.setId(userID);
        user.setLastName(request.getParameter(LAST_NAME).trim());
        user.setFirstName(request.getParameter(FIRST_NAME).trim());
        user.setFatherName(request.getParameter(FATHER_NAME).trim());
        user.setPassport(request.getParameter(PASSPORT).replaceAll(REGEX_WHITESPACE_CHARACTERS, ""));
        Date date = Date.valueOf(request.getParameter(BIRTHDAY));
        user.setBirthday(date);
        user.setGender(Gender.valueOf(request.getParameter(GENDER)));

        user.setAddress(request.getParameter(ADDRESS).trim());
        user.setPhone(request.getParameter(PHONE).replaceAll(REGEX_WHITESPACE_CHARACTERS, ""));
        user.setEmail(request.getParameter(EMAIL).trim());
        user.setNickname(request.getParameter(NICKNAME).trim());

        user.setRole(Role.valueOf(request.getParameter(ROLE)));

        String currentPassword = request.getParameter(CURRENT_PASSWORD);
        String newPassword = request.getParameter(NEW_PASSWORD);
        String repeatedNewPassword = request.getParameter(REPEATED_NEW_PASSWORD);


        try {
            user.setImageURL(userService.getUser(userID).getImageURL());//TODO

            userService.updateUser(user, currentPassword, newPassword, repeatedNewPassword);

            if (userID == sessionUser.getId()) {
                SessionUserProvider.removeSessionUser(request);
                sessionUser = userService.getSessionUser(userID);
                SessionUserProvider.setSessionUser(request, sessionUser);
            }

        } catch (UpdateUserException e) {
            HttpSession session = request.getSession();
            session.setAttribute(USER, user);
            session.setAttribute(MESSAGE, errorMessageBuilder(e));

        } catch (ServiceException e) {
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
            return;
        }

        if (request.getParameter(USER_ID) != null && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
            RequestProvider.redirect(String.format(RedirectCommand.USER_PAGE, REDIRECT_USER_ID + userID), request, response);
        } else if (request.getParameter(READER_ID) != null && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
            RequestProvider.redirect(String.format(RedirectCommand.READER_PAGE, userID), request, response);
        } else {
            RequestProvider.redirect(String.format(RedirectCommand.USER_PAGE, ""), request, response);
        }
    }

    private String errorMessageBuilder(UpdateUserException e) {
        return "Data input incorrectly";//TODO
    }
}
