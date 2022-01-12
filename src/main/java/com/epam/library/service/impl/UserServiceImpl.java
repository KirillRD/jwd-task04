package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.UserDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.User;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl() {
        userDAO = DAOProvider.getInstance().getUserDAO();
    }

    @Override
    public User registration(User user, String password, String repeatedPassword) throws ServiceException {
        if (password.equals(repeatedPassword)) {
            try {
                String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
                return userDAO.registration(user, hashed);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        } else {
            return null;
        }
    }

    @Override
    public User authentication(String email, String password) throws ServiceException {
        try {
            return userDAO.authentication(email, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateUser(User user) throws ServiceException {
        try {
            return userDAO.updateUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updatePassword(int userID, String newPassword, String repeatedNewPassword, String oldPassword) throws ServiceException {
        if (newPassword.equals(repeatedNewPassword)) {
            try {
                return userDAO.updatePassword(userID, newPassword, oldPassword);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        } else {
            return false;
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
