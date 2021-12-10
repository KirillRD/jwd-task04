package com.epam.library.service;

import com.epam.library.entity.user.RegistrationInfo;
import com.epam.library.entity.User;
import com.epam.library.service.exception.ServiceException;

public interface UserService {
    User registration(RegistrationInfo registrationInfo, String repeatedPassword) throws ServiceException;
}
