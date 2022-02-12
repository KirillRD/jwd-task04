package com.epam.library.service;

import com.epam.library.entity.User;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.entity.user.UserInfo;
import com.epam.library.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * User service interface
 */
public interface UserService {
    /**
     * Returns ID of the user who was successfully registered
     * @param user to be registered
     * @param password entered password
     * @param repeatedPassword entered repeated password
     * @return ID of the user who was successfully registered
     * @throws ServiceException if a data processing error occurs
     */
    int registration(UserInfo user, String password, String repeatedPassword) throws ServiceException;

    /**
     * Returns ID of the user whose password and email address match the entered
     * @param email entered email
     * @param password entered password
     * @return ID of the user whose password and email address match the entered
     * @throws ServiceException if a data processing error occurs
     */
    Integer authentication(String email, String password) throws ServiceException;

    /**
     * Updating the user's data and password
     * @param user whose data needs to be updated
     * @param currentPassword entered current password
     * @param newPassword entered new password
     * @param repeatedNewPassword entered repeated new password
     * @throws ServiceException if a data processing error occurs
     */
    void updateUser(UserInfo user, String currentPassword, String newPassword, String repeatedNewPassword) throws ServiceException;

    /**
     * Updating the user's image
     * @param userID ID of the user whose image needs to be updated
     * @param imageURL user image
     * @throws ServiceException if a data processing error occurs
     */
    void updateUserImage(int userID, String imageURL) throws ServiceException;

    /**
     * Returns true if the user is locked
     * @param userID ID of the user whose locking is to be checked
     * @return true if the user is locked
     * @throws ServiceException if a data processing error occurs
     */
    boolean userIsLock(int userID) throws ServiceException;

    /**
     * Locking/Unlocking user
     * @param userID ID of the user whose lock should be changed
     * @throws ServiceException if a data processing error occurs
     */
    void lockUser(int userID) throws ServiceException;

    /**
     * Returns user's data
     * @param userID ID of the user whose data need to get
     * @return user's data
     * @throws ServiceException if a data processing error occurs
     */
    User getUser(int userID) throws ServiceException;

    /**
     * Returns filtered list with users' data
     * @param filters user filtering options
     * @param page page number
     * @return filtered list with users' data
     * @throws ServiceException if a data processing error occurs
     */
    List<User> getUsersByFilter(Map<String, Object> filters, int page) throws ServiceException;

    /**
     * Returns the number of pages in the filtered user list
     * @param filters user filtering options
     * @return the number of pages in the filtered user list
     * @throws ServiceException if a data processing error occurs
     */
    int getPagesCount(Map<String, Object> filters) throws ServiceException;

    /**
     * Returns user's data for session
     * @param userID ID of the user whose data need to get
     * @return user's data for session
     * @throws ServiceException if a data processing error occurs
     */
    SessionUser getSessionUser(int userID) throws ServiceException;
}
