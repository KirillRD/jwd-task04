package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.User;
import com.epam.library.entity.user.SessionUser;

import java.util.List;
import java.util.Map;

public interface UserDAO {
    User registration(User user, String password) throws DAOException;
    User authentication(String email, String password) throws DAOException;
    boolean updateUser(User user) throws DAOException;
    boolean updatePassword(int userID, String newPassword, String oldPassword) throws DAOException;
    User getUser(int userID) throws DAOException;
    List<User> getUsersByFilter(Map<String, Object> filters) throws DAOException;
    SessionUser getSessionUser(int userID) throws DAOException;
}
