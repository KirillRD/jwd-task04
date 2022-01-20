package com.epam.library.service;

import com.epam.library.entity.User;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.entity.user.UserInfo;
import com.epam.library.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface UserService {
    int registration(UserInfo user, String password, String repeatedPassword) throws ServiceException;
    Integer authentication(String email, String password) throws ServiceException;
    void updateUser(UserInfo user, String currentPassword, String newPassword, String repeatedNewPassword) throws ServiceException;
    User getUser(int userID) throws ServiceException;
    List<User> getUsersByFilter(Map<String, Object> filters) throws ServiceException;
    SessionUser getSessionUser(int userID) throws ServiceException;
}
