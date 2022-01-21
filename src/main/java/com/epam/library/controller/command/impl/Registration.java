package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.entity.user.UserInfo;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.UserException;
import com.epam.library.service.exception.user.*;
import com.epam.library.service.exception.user.length.*;
import com.epam.library.service.exception.user.password.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Registration implements Command {
    private static final Logger logger = Logger.getLogger(Registration.class.getName());

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

    private static final String USER = "user";
    private static final String MESSAGES = "messages";
    private static final Map<String, String> exceptionMessages = Map.ofEntries(
            Map.entry(ExistEmailException.class.getSimpleName(), "error-exist-email"),
            Map.entry(EmptyFirstNameException.class.getSimpleName(), "error-empty-first-name"),
            Map.entry(EmptyLastNameException.class.getSimpleName(), "error-empty-last-name"),
            Map.entry(EmptyNicknameException.class.getSimpleName(), "error-empty-nickname"),
            Map.entry(InvalidBirthdayFormatException.class.getSimpleName(), "error-birthday-format"),
            Map.entry(InvalidEmailFormatException.class.getSimpleName(), "error-email-format"),
            Map.entry(InvalidLengthLastNameException.class.getSimpleName(), "error-length-last-name"),
            Map.entry(InvalidLengthFirstNameException.class.getSimpleName(), "error-length-first-name"),
            Map.entry(InvalidLengthFatherNameException.class.getSimpleName(), "error-length-father-name"),
            Map.entry(InvalidLengthNicknameException.class.getSimpleName(), "error-length-nickname"),
            Map.entry(InvalidLengthAddressException.class.getSimpleName(), "error-length-address"),
            Map.entry(InvalidPassportFormatException.class.getSimpleName(), "error-passport-format"),
            Map.entry(InvalidPhoneFormatException.class.getSimpleName(), "error-phone-format"),
            Map.entry(EmptyPasswordException.class.getSimpleName(), "error-empty-password"),
            Map.entry(EmptyRepeatedPasswordException.class.getSimpleName(), "error-empty-repeated-password"),
            Map.entry(NotEqualPasswordException.class.getSimpleName(), "error-not-equal-password"),
            Map.entry(EmptyGenderException.class.getSimpleName(), "error-empty-gender"),
            Map.entry(InvalidGenderFormatException.class.getSimpleName(), "error-gender-format"),
            Map.entry(EmptyEmailException.class.getSimpleName(), "error-empty-email"),
            Map.entry(EmptyBirthdayException.class.getSimpleName(), "error-empty-birthday")
    );

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(logMessageBuilder("Registration started", request));
        UserInfo user = new UserInfo();
        user.setNickname(request.getParameter(NICKNAME).trim());
        String password = request.getParameter(PASSWORD).trim();
        String repeatedPassword = request.getParameter(REPEATED_PASSWORD).trim();
        user.setEmail(request.getParameter(EMAIL).trim());
        user.setLastName(request.getParameter(LAST_NAME).trim());
        user.setFirstName(request.getParameter(FIRST_NAME).trim());
        user.setFatherName(request.getParameter(FATHER_NAME).trim());
        user.setBirthday(request.getParameter(BIRTHDAY));
        user.setGender(request.getParameter(GENDER));
        user.setPassport(request.getParameter(PASSPORT).replaceAll(REGEX_WHITESPACE_CHARACTERS, ""));
        user.setAddress(request.getParameter(ADDRESS).trim());
        user.setPhone(request.getParameter(PHONE).replaceAll(REGEX_WHITESPACE_CHARACTERS, ""));

        UserService userService = ServiceProvider.getInstance().getUserService();
        try {
            int userID = userService.registration(user, password, repeatedPassword);
            logger.info(logMessageBuilder("Registration completed", request));
            SessionUser sessionUser = userService.getSessionUser(userID);
            SessionUserProvider.setSessionUser(request, sessionUser);

            RequestProvider.redirect(RedirectCommand.MAIN_PAGE, request, response);
        } catch (UserException e) {
            HttpSession session = request.getSession();
            session.setAttribute(USER, user);
            session.setAttribute(MESSAGES, errorMessageBuilder(e));
            logger.info(logMessageBuilder("The entered data is invalid. Registration was not completed", request));
            RequestProvider.redirect(RedirectCommand.REGISTRATION_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(logMessageBuilder("Error data registration", request), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }

    private List<String> errorMessageBuilder(UserException exception) {
        List<String> messages = new ArrayList<>();
        for (UserException e : exception.getExceptions()) {
            messages.add(exceptionMessages.get(e.getClass().getSimpleName()));
        }
        return messages;
    }
}
