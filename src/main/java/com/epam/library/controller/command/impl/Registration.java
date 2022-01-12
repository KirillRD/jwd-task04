package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.entity.User;
import com.epam.library.entity.user.Gender;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

public class Registration implements Command {

    private static final String NICKNAME = "nickname";
    private static final String PASSWORD = "password";
    private static final String REPEATED_PASSWORD = "repeated_password";
    private static final String EMAIL = "email";
    private static final String LAST_NAME = "last_name";
    private static final String FIRST_NAME = "first_name";
    private static final String FATHER_NAME = "father_name";
    private static final String BIRTHDAY = "birthday";
    private static final String GENDER = "gender";
    private static final String PASSPORT = "passport";
    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    private static final String REGEX_WHITESPACE_CHARACTERS = "\\s+";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setNickname(request.getParameter(NICKNAME).trim());
        String password = request.getParameter(PASSWORD).trim();
        String repeatedPassword = request.getParameter(REPEATED_PASSWORD).trim();
        user.setEmail(request.getParameter(EMAIL).trim());
        user.setLastName(request.getParameter(LAST_NAME).trim());
        user.setFirstName(request.getParameter(FIRST_NAME).trim());
        user.setFatherName(request.getParameter(FATHER_NAME).trim());
        Date date = Date.valueOf(request.getParameter(BIRTHDAY));
        user.setBirthday(date);
        user.setGender(Gender.valueOf(request.getParameter(GENDER)));
        user.setPassport(request.getParameter(PASSPORT).replaceAll(REGEX_WHITESPACE_CHARACTERS, ""));
        user.setAddress(request.getParameter(ADDRESS).trim());
        user.setPhone(request.getParameter(PHONE).replaceAll(REGEX_WHITESPACE_CHARACTERS, ""));

        UserService userService = ServiceProvider.getInstance().getUserService();
        try {
            userService.registration(user, password, repeatedPassword);
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.redirect(RedirectCommand.MAIN_PAGE, request, response);
    }
}
