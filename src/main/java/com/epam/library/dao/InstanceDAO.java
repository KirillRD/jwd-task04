package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.instance.BookInstance;
import com.epam.library.entity.Instance;

import java.util.List;

/**
 * Instance DAO interface
 */
public interface InstanceDAO {
    /**
     * Returns true if DB contains instance with the specified number
     * @param instanceID ID of the instance whose presence is to be checked
     * @param number number of the instance whose presence is to be checked
     * @return true if DB contains instance with the specified number
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean instanceNumberExists(int instanceID, String number) throws DAOException;

    /**
     * Adding an instance
     * @param instance to be added
     * @throws DAOException if an error occurred while accessing the database
     */
    void addInstance(Instance instance) throws DAOException;

    /**
     * Updating the instance's data
     * @param instance whose data needs to be updated
     * @throws DAOException if an error occurred while accessing the database
     */
    void updateInstance(Instance instance) throws DAOException;

    /**
     * Returns true if instance was deleted successfully
     * @param instanceID ID of the instance to be deleted
     * @return true if instance was deleted successfully
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean deleteInstance(int instanceID) throws DAOException;

    /**
     * Returns instance's data
     * @param instanceID ID of the instance whose data need to get
     * @return instance's data
     * @throws DAOException if an error occurred while accessing the database
     */
    BookInstance getBookInstance(int instanceID) throws DAOException;

    /**
     * Returns list with the instances' data
     * @param bookID ID of the book whose instance list is returned
     * @return list with the instances' data
     * @throws DAOException if an error occurred while accessing the database
     */
    List<BookInstance> getBookInstances(int bookID) throws DAOException;
}
