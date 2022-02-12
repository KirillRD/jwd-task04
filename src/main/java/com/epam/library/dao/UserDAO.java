package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.User;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.entity.user.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * User DAO interface
 */
public interface UserDAO {
    /**
     * Returns ID of the user who was successfully registered
     * @param user to be registered
     * @param password entered password
     * @return ID of the user who was successfully registered
     * @throws DAOException if an error occurred while accessing the database
     */
    int registration(UserInfo user, String password) throws DAOException;

    /**
     * Returns ID of the user whose password and email address match the entered
     * @param email entered email
     * @param password entered password
     * @return ID of the user whose password and email address match the entered
     * @throws DAOException if an error occurred while accessing the database
     */
    Integer authentication(String email, String password) throws DAOException;

    /**
     * Returns true if DB contains user with the specified email
     * @param userID ID of the user whose presence is to be checked
     * @param email email of the user whose presence is to be checked
     * @return true if DB contains contains user with the specified email
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean emailExists(int userID, String email) throws DAOException;

    /**
     * Returns true if the entered password equal to the current password
     * @param userID ID of the user whose entered password is to be checked
     * @param currentPassword entered password
     * @return true if the entered password equal to the current password
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean equalCurrentPassword(int userID, String currentPassword) throws DAOException;

    /**
     * Returns true if the user is locked
     * @param userID ID of the user whose locking is to be checked
     * @return true if the user is locked
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean userIsLock(int userID) throws DAOException;

    /**
     * Locking/Unlocking user
     * @param userID ID of the user whose lock should be changed
     * @throws DAOException if an error occurred while accessing the database
     */
    void lockUser(int userID) throws DAOException;

    /**
     * Updating the user's data and password
     * @param user whose data needs to be updated
     * @param newPassword entered new password
     * @throws DAOException if an error occurred while accessing the database
     */
    void updateUser(UserInfo user, String newPassword) throws DAOException;

    /**
     * Updating the user's data
     * @param user whose data needs to be updated
     * @throws DAOException if an error occurred while accessing the database
     */
    void updateUser(UserInfo user) throws DAOException;

    /**
     * Updating the user's image
     * @param userID ID of the user whose image needs to be updated
     * @param imageURL user image
     * @throws DAOException if an error occurred while accessing the database
     */
    void updateUserImage(int userID, String imageURL) throws DAOException;

    /**
     * Returns user's data
     * @param userID ID of the user whose data need to get
     * @return user's data
     * @throws DAOException if an error occurred while accessing the database
     */
    User getUser(int userID) throws DAOException;

    /**
     * Returns filtered list with users' data
     * @param filters user filtering options
     * @param page page number
     * @return filtered list with users' data
     * @throws DAOException if an error occurred while accessing the database
     */
    List<User> getUsersByFilter(Map<String, Object> filters, int page) throws DAOException;

    /**
     * Returns the number of pages in the filtered user list
     * @param filters user filtering options
     * @return the number of pages in the filtered user list
     * @throws DAOException if an error occurred while accessing the database
     */
    int getPagesCount(Map<String, Object> filters) throws DAOException;

    /**
     * Returns user's data for session
     * @param userID ID of the user whose data need to get
     * @return user's data for session
     * @throws DAOException if an error occurred while accessing the database
     */
    SessionUser getSessionUser(int userID) throws DAOException;
}
