package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.UserDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.User;
import com.epam.library.entity.user.Gender;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.entity.user.UserInfo;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.*;
import com.epam.library.service.exception.user.length.*;
import com.epam.library.service.exception.user.password.*;
import com.epam.library.service.exception.user.*;
import com.epam.library.service.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private static final UserDAO userDAO = DAOProvider.getInstance().getUserDAO();
    private static final Validator validator = Validator.getInstance();

    private static final int LAST_NAME_LENGTH = 30;
    private static final int FIRST_NAME_LENGTH = 30;
    private static final int FATHER_NAME_LENGTH = 30;
    private static final int NICKNAME_LENGTH = 20;
    private static final int PASSPORT_LENGTH = 20;
    private static final int ADDRESS_LENGTH = 100;

    public UserServiceImpl() {}

    @Override
    public int registration(UserInfo user, String password, String repeatedPassword) throws ServiceException {

        List<UserException> exceptions = new ArrayList<>();
        try {
            if (validator.isEmail(user.getEmail())) {
                if (!userDAO.checkEmail(user.getId(), user.getEmail())) {
                    exceptions.add(new ExistEmailException());
                }
            } else {
                if (validator.isEmpty(user.getEmail())) {
                    exceptions.add(new EmptyEmailException());
                } else {
                    exceptions.add(new InvalidEmailFormatException());
                }
            }

            if (validator.isEmpty(password)) {
                exceptions.add(new EmptyPasswordException());
            }

            if (validator.isEmpty(repeatedPassword)) {
                exceptions.add(new EmptyRepeatedPasswordException());
            }

            if (!validator.isEmpty(password) && !validator.isEmpty(repeatedPassword) && !password.equals(repeatedPassword)) {
                exceptions.add(new NotEqualPasswordException());
            }

            if (validator.isEmpty(user.getNickname())) {
                exceptions.add(new EmptyNicknameException());
            } else if (user.getNickname().length() > NICKNAME_LENGTH) {
                exceptions.add(new InvalidLengthNicknameException());
            }

            if (validator.isEmpty(user.getLastName())) {
                exceptions.add(new EmptyLastNameException());
            } else if (user.getFatherName().length() > LAST_NAME_LENGTH) {
                exceptions.add(new InvalidLengthLastNameException());
            }

            if (validator.isEmpty(user.getFirstName())) {
                exceptions.add(new EmptyFirstNameException());
            } else if (user.getFirstName().length() > FIRST_NAME_LENGTH) {
                exceptions.add(new InvalidLengthFirstNameException());
            }

            if (user.getFatherName().length() > FATHER_NAME_LENGTH) {
                exceptions.add(new InvalidLengthFatherNameException());
            }

            if (validator.isEmpty(user.getBirthday())) {
                exceptions.add(new EmptyBirthdayException());
            } else if (!validator.isDate(user.getBirthday())) {
                exceptions.add(new InvalidBirthdayFormatException());
            }

            if (validator.isEmpty(user.getGender())) {
                exceptions.add(new EmptyGenderException());
            } else if (!Gender.containsGender(user.getGender())) {
                exceptions.add(new InvalidGenderFormatException());
            }

            if (user.getPassport().length() > PASSPORT_LENGTH) {
                exceptions.add(new InvalidPassportFormatException());
            }

            if (user.getAddress().length() > ADDRESS_LENGTH) {
                exceptions.add(new InvalidLengthAddressException());
            }

            if (!validator.isPhone(user.getPhone())) {
                exceptions.add(new InvalidPhoneFormatException());
            }

            if (exceptions.isEmpty()) {
                return userDAO.registration(user, password);
            } else {
                throw new UserException(exceptions);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer authentication(String email, String password) throws ServiceException {
        try {
            return userDAO.authentication(email, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUser(UserInfo user, String currentPassword, String newPassword, String repeatedNewPassword) throws ServiceException {

        List<UserException> exceptions = new ArrayList<>();
        try {
            if (validator.isEmpty(user.getLastName())) {
                exceptions.add(new EmptyLastNameException());
            } else if (user.getFatherName().length() > LAST_NAME_LENGTH) {
                exceptions.add(new InvalidLengthLastNameException());
            }

            if (validator.isEmpty(user.getFirstName())) {
                exceptions.add(new EmptyFirstNameException());
            } else if (user.getFirstName().length() > FIRST_NAME_LENGTH) {
                exceptions.add(new InvalidLengthFirstNameException());
            }

            if (user.getFatherName().length() > FATHER_NAME_LENGTH) {
                exceptions.add(new InvalidLengthFatherNameException());
            }

            if (validator.isEmpty(user.getBirthday())) {
                exceptions.add(new EmptyBirthdayException());
            } else if (!validator.isDate(user.getBirthday())) {
                exceptions.add(new InvalidBirthdayFormatException());
            }

            if (validator.isEmpty(user.getGender())) {
                exceptions.add(new EmptyGenderException());
            } else if (!Gender.containsGender(user.getGender())) {
                exceptions.add(new InvalidGenderFormatException());
            }

            if (user.getPassport().length() > PASSPORT_LENGTH) {
                exceptions.add(new InvalidPassportFormatException());
            }


            if (user.getAddress().length() > ADDRESS_LENGTH) {
                exceptions.add(new InvalidLengthAddressException());
            }

            if (!validator.isPhone(user.getPhone())) {
                exceptions.add(new InvalidPhoneFormatException());
            }

            if (validator.isEmail(user.getEmail())) {
                if (!userDAO.checkEmail(user.getId(), user.getEmail())) {
                    exceptions.add(new ExistEmailException());
                }
            } else {
                if (validator.isEmpty(user.getEmail())) {
                    exceptions.add(new EmptyEmailException());
                } else {
                    exceptions.add(new InvalidEmailFormatException());
                }
            }

            if (validator.isEmpty(user.getNickname())) {
                exceptions.add(new EmptyNicknameException());
            } else if (user.getNickname().length() > NICKNAME_LENGTH) {
                exceptions.add(new InvalidLengthNicknameException());
            }


            if (!validator.isEmpty(currentPassword) || !validator.isEmpty(newPassword) || !validator.isEmpty(repeatedNewPassword)) {
                if (validator.isEmpty(currentPassword)) {
                    exceptions.add(new EmptyCurrentPasswordException());
                } else if (!userDAO.checkPassword(user.getId(), currentPassword)) {
                    exceptions.add(new InvalidCurrentPasswordException());
                }
                if (validator.isEmpty(newPassword)) {
                    exceptions.add(new EmptyNewPasswordException());
                }
                if (validator.isEmpty(repeatedNewPassword)) {
                    exceptions.add(new EmptyRepeatedNewPasswordException());
                }
                if (!validator.isEmpty(newPassword) && !validator.isEmpty(repeatedNewPassword) && !newPassword.equals(repeatedNewPassword)) {
                    exceptions.add(new NotEqualNewPasswordException());
                }
            }

            if (exceptions.isEmpty()) {
                if (validator.isEmpty(currentPassword) && validator.isEmpty(newPassword) && validator.isEmpty(repeatedNewPassword)) {
                    userDAO.updateUser(user);
                } else {
                    userDAO.updateUser(user, currentPassword, newPassword);
                }
            } else {
                throw new UserException(exceptions);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUser(int userID) throws ServiceException {
        try {
            return userDAO.getUser(userID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getUsersByFilter(Map<String, Object> filters) throws ServiceException {
        try {
            return userDAO.getUsersByFilter(filters);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public SessionUser getSessionUser(int userID) throws ServiceException {
        try {
            return userDAO.getSessionUser(userID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
