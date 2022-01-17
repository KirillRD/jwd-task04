package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.UserDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.dao.exception.ExistEmailDAOException;
import com.epam.library.dao.exception.InvalidCurrentPasswordDAOException;
import com.epam.library.dao.exception.UpdateUserDAOException;
import com.epam.library.entity.User;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ExistEmailException;
import com.epam.library.service.exception.InvalidCurrentPasswordException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.UpdateUserException;
import com.epam.library.service.exception.password.EmptyCurrentPasswordException;
import com.epam.library.service.exception.password.EmptyNewPasswordException;
import com.epam.library.service.exception.password.EmptyRepeatedNewPasswordException;
import com.epam.library.service.exception.password.NotEqualNewPasswordException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    public UserServiceImpl() {
        userDAO = DAOProvider.getInstance().getUserDAO();
    }

    @Override
    public boolean registration(User user, String password, String repeatedPassword) throws ServiceException {
        if (password.equals(repeatedPassword)) {
            try {
                String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
                return userDAO.registration(user, hashed);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        } else {
            return false;
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
    public void updateUser(User user, String currentPassword, String newPassword, String repeatedNewPassword) throws ServiceException {

        List<UpdateUserException> exceptions = new ArrayList<>();
        try {

            if (currentPassword != null || newPassword != null || repeatedNewPassword != null) {
                if (newPassword == null) {
                    exceptions.add(new EmptyCurrentPasswordException());
                }
                if (repeatedNewPassword == null) {
                    exceptions.add(new EmptyNewPasswordException());
                }
                if (currentPassword == null) {
                    exceptions.add(new EmptyRepeatedNewPasswordException());
                }
                if (newPassword != null && !newPassword.equals(repeatedNewPassword)) {
                    exceptions.add(new NotEqualNewPasswordException());
                } else if (repeatedNewPassword != null && !repeatedNewPassword.equals(newPassword)) {
                    exceptions.add(new NotEqualNewPasswordException());
                }
            }

            if (currentPassword == null && newPassword == null && repeatedNewPassword == null) {
                try {
                    userDAO.updateUser(user);
                } catch (InvalidCurrentPasswordDAOException e) {
                    exceptions.add(new InvalidCurrentPasswordException(e));
                }
            } else if (currentPassword != null && newPassword != null && newPassword.equals(repeatedNewPassword)) {
                try {
                    userDAO.updateUser(user, currentPassword, newPassword);
                } catch (UpdateUserDAOException e) {
                    exceptions.add(new UpdateUserException());
                } catch (InvalidCurrentPasswordDAOException e) {
                    exceptions.add(new InvalidCurrentPasswordException(e));
                } catch (ExistEmailDAOException e) {
                    exceptions.add(new ExistEmailException(e));
                }
            }

            if (!exceptions.isEmpty()) {
                throw new UpdateUserException(exceptions);
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
