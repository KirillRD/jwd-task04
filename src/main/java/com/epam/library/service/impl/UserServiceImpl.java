package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.UserDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.user.RegistrationInfo;
import com.epam.library.entity.User;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceImpl implements UserService {

    public UserServiceImpl() {}

    @Override
    public User registration(RegistrationInfo registrationInfo, String repeatedPassword) throws ServiceException {
        if (registrationInfo.getPassword().equals(repeatedPassword)) {
            User user;
            try {
                DAOProvider daoProvider = DAOProvider.getInstance();
                UserDAO userDAO = daoProvider.getUserDAO();
                registrationInfo.setPassword(BCrypt.hashpw(registrationInfo.getPassword(), BCrypt.gensalt()));
                user = userDAO.registration(registrationInfo);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
            return user;
        } else {
            return null;
        }
    }
}
