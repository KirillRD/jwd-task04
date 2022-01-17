package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.User;
import com.epam.library.entity.user.SessionUser;

import java.util.List;
import java.util.Map;

public interface UserDAO {
    boolean registration(User user, String password) throws DAOException;
    Integer authentication(String email, String password) throws DAOException;
    void updateUser(User user, String currentPassword, String newPassword) throws DAOException;
    void updateUser(User user) throws DAOException;
    boolean updatePassword(int userID, String currentPassword, String newPassword) throws DAOException;
    User getUser(int userID) throws DAOException;
    List<User> getUsersByFilter(Map<String, Object> filters) throws DAOException;
    SessionUser getSessionUser(int userID) throws DAOException;
}
