package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.User;

public interface UserDAO {
    User registrationUser(User user) throws DAOException;
    User authorizationUser(String email, String password) throws DAOException;
    User updateUser(User user) throws DAOException;
    User changePassword(User user, String password) throws DAOException;
}
