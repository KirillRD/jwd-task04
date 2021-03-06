package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.User;
import com.epam.library.entity.user.constant.Role;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.entity.user.UserInfo;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ValidationException;
import com.epam.library.service.exception.validation.UserValidationException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.validation.user.*;
import com.epam.library.service.exception.validation.user.length.*;
import com.epam.library.service.exception.validation.user.password.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Command to edit user
 */
public class EditUser implements Command {
    private static final Logger logger = Logger.getLogger(EditUser.class.getName());

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
    private static final String REDIRECT_READER_ID = "&reader_id=";
    private static final String MESSAGES = "messages";
    private static final Map<String, String> exceptionMessages = Map.ofEntries(
            Map.entry(ExistEmailException.class.getSimpleName(), "error-exist-email"),
            Map.entry(InvalidCurrentPasswordException.class.getSimpleName(), "error-current-password"),
            Map.entry(EmptyCurrentPasswordException.class.getSimpleName(), "error-empty-current-password"),
            Map.entry(EmptyNewPasswordException.class.getSimpleName(), "error-empty-new-password"),
            Map.entry(EmptyRepeatedNewPasswordException.class.getSimpleName(), "error-empty-repeated-new-password"),
            Map.entry(NotEqualNewPasswordException.class.getSimpleName(), "error-not-equal-new-password"),
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
            Map.entry(EmptyGenderException.class.getSimpleName(), "error-empty-gender"),
            Map.entry(InvalidGenderFormatException.class.getSimpleName(), "error-gender-format"),
            Map.entry(EmptyEmailException.class.getSimpleName(), "error-empty-email"),
            Map.entry(EmptyBirthdayException.class.getSimpleName(), "error-empty-birthday"),
            Map.entry(InvalidBirthdayException.class.getSimpleName(), "error-birthday")
    );

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "User update started"));

        UserService userService = ServiceProvider.getInstance().getUserService();

        int userID;
        SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
        if (Util.isID(request.getParameter(USER_ID)) && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
            userID = Integer.parseInt(request.getParameter(USER_ID));
            logger.info(LogMessageBuilder.message(logMessage, "Admin/Librarian update user"));
        } else if (Util.isID(request.getParameter(READER_ID)) && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
            userID = Integer.parseInt(request.getParameter(READER_ID));
            logger.info(LogMessageBuilder.message(logMessage, "Admin/Librarian update user"));
        } else {
            userID = sessionUser.getId();
            logger.info(LogMessageBuilder.message(logMessage, "User update himself"));
        }

        UserInfo user = new UserInfo();
        user.setId(userID);
        try {
            User userData = userService.getUser(userID);
            if (sessionUser.getRole() == Role.READER) {
                user.setLastName(userData.getLastName());
                user.setFirstName(userData.getFirstName());
                user.setFatherName(userData.getFatherName());
                user.setPassport(userData.getPassport());
                user.setBirthday(userData.getBirthday().toString());
                user.setGender(userData.getGender().toString());
            } else {
                user.setLastName(request.getParameter(LAST_NAME).trim());
                user.setFirstName(request.getParameter(FIRST_NAME).trim());
                user.setFatherName(request.getParameter(FATHER_NAME).trim());
                user.setPassport(request.getParameter(PASSPORT).replaceAll(REGEX_WHITESPACE_CHARACTERS, ""));
                user.setBirthday(request.getParameter(BIRTHDAY));
                user.setGender(request.getParameter(GENDER));
            }

            user.setAddress(request.getParameter(ADDRESS).trim());
            user.setPhone(request.getParameter(PHONE).replaceAll(REGEX_WHITESPACE_CHARACTERS, ""));

            if (userID != sessionUser.getId() && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
                user.setEmail(userData.getEmail());
                user.setNickname(userData.getNickname());
            } else {
                user.setEmail(request.getParameter(EMAIL).trim());
                user.setNickname(request.getParameter(NICKNAME).trim());
            }

            if (sessionUser.getRole() == Role.READER || sessionUser.getRole() == Role.LIBRARIAN) {
                user.setRole(userData.getRole());
            } else {
                user.setRole(Role.valueOf(request.getParameter(ROLE)));
            }

            String currentPassword = request.getParameter(CURRENT_PASSWORD);
            String newPassword = request.getParameter(NEW_PASSWORD);
            String repeatedNewPassword = request.getParameter(REPEATED_NEW_PASSWORD);

            user.setImageURL(userData.getImageURL());

            userService.updateUser(user, currentPassword, newPassword, repeatedNewPassword);
            logger.info(LogMessageBuilder.message(logMessage, "User update completed"));

            if (userID == sessionUser.getId()) {
                SessionUserProvider.removeSessionUser(request);
                sessionUser = userService.getSessionUser(userID);
                SessionUserProvider.setSessionUser(request, sessionUser);
            }

            if (Util.isID(request.getParameter(USER_ID)) && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
                RequestManager.redirect(String.format(RedirectCommand.USER_PAGE, REDIRECT_USER_ID + userID), request, response);
            } else if (Util.isID(request.getParameter(READER_ID)) && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
                RequestManager.redirect(String.format(RedirectCommand.READER_PAGE, userID), request, response);
            } else {
                RequestManager.redirect(String.format(RedirectCommand.USER_PAGE, ""), request, response);
            }
        } catch (UserValidationException e) {
            HttpSession session = request.getSession();
            session.setAttribute(USER, user);
            session.setAttribute(MESSAGES, errorMessageBuilder(e));
            logger.info(LogMessageBuilder.message(logMessage, "The entered data is invalid. User was not updated"));

            if (Util.isID(request.getParameter(USER_ID)) && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
                RequestManager.redirect(String.format(RedirectCommand.EDIT_USER_PAGE, REDIRECT_USER_ID + userID), request, response);
            } else if (Util.isID(request.getParameter(READER_ID)) && (sessionUser.getRole() == Role.LIBRARIAN || sessionUser.getRole() == Role.ADMIN)) {
                RequestManager.redirect(String.format(RedirectCommand.EDIT_USER_PAGE, REDIRECT_READER_ID + userID), request, response);
            } else {
                RequestManager.redirect(String.format(RedirectCommand.EDIT_USER_PAGE, ""), request, response);
            }
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error updating user data"), e);
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }

    private List<String> errorMessageBuilder(UserValidationException exception) {
        List<String> messages = new ArrayList<>();
        for (ValidationException e : exception.getExceptions()) {
            messages.add(exceptionMessages.get(e.getClass().getSimpleName()));
        }
        return messages;
    }
}
