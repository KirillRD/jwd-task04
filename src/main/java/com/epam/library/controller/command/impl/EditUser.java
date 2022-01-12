package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.User;
import com.epam.library.entity.user.Gender;
import com.epam.library.entity.user.Role;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    private static final String OLD_PASSWORD = "old_password";
    private static final String NEW_PASSWORD = "new_password";
    private static final String REPEATED_NEW_PASSWORD = "repeated_new_password";

    private static final String ROLE = "role";

    private static final String REGEX_WHITESPACE_CHARACTERS = "\\s+";

    private static final String REDIRECT_USER_ID = "&user_id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = ServiceProvider.getInstance().getUserService();

        User user = new User();
        int id;
        SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
        if (request.getParameter(USER_ID) != null && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
            id = Integer.parseInt(request.getParameter(USER_ID));
        } else if (request.getParameter(READER_ID) != null && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
            id = Integer.parseInt(request.getParameter(READER_ID));
        } else {
            id = sessionUser.getId();
        }
        user.setId(id);
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

        String oldPassword = request.getParameter(OLD_PASSWORD);
        String newPassword = request.getParameter(NEW_PASSWORD);
        String repeatedNewPassword = request.getParameter(REPEATED_NEW_PASSWORD);

        try {
            if (request.getParameter(ROLE) != null) {
                user.setRole(Role.valueOf(request.getParameter(ROLE)));
            } else {
                user.setRole(userService.getUser(id).getRole());
            }

            user.setImageURL(userService.getUser(id).getImageURL());//TODO временная установка картинки

            if (userService.updateUser(user)) {
                //request.setAttribute("имя ошибки", "надпись ошибки");
            }

            if ((oldPassword != null && !oldPassword.isEmpty()) &&
                (newPassword != null && !newPassword.isEmpty()) &&
                (repeatedNewPassword != null && !repeatedNewPassword.isEmpty())) {
                if (!userService.updatePassword(user.getId(), newPassword, repeatedNewPassword, oldPassword)) {
                    //request.setAttribute("имя ошибки", "надпись ошибки");
                }
            }

            if (id == sessionUser.getId()) {
                SessionUserProvider.removeSessionUser(request);
                sessionUser = userService.getSessionUser(id);
                SessionUserProvider.setSessionUser(request, sessionUser);
            }

            request.setAttribute(USER, user);
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        if (request.getParameter(USER_ID) != null) {
            RequestProvider.redirect(String.format(RedirectCommand.USER_PAGE, REDIRECT_USER_ID + id), request, response);
        } else if (request.getParameter(READER_ID) != null) {
            RequestProvider.redirect(String.format(RedirectCommand.READER_PAGE, id), request, response);
        } else {
            RequestProvider.redirect(String.format(RedirectCommand.USER_PAGE, ""), request, response);
        }
    }
}
