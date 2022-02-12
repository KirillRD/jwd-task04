package com.epam.library.service;

import com.epam.library.entity.instance.BookInstance;
import com.epam.library.entity.Instance;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

/**
 * Instance service interface
 */
public interface InstanceService {
    /**
     *
     * Adding an instance
     * @param instance to be added
     * @throws ServiceException if a data processing error occurs
     */
    void addInstance(Instance instance) throws ServiceException;

    /**
     * Updating the instance's data
     * @param instance whose data needs to be updated
     * @throws ServiceException if a data processing error occurs
     */
    void updateInstance(Instance instance) throws ServiceException;

    /**
     * Returns true if instance was deleted successfully
     * @param instanceID ID of the instance to be deleted
     * @return true if instance was deleted successfully
     * @throws ServiceException if a data processing error occurs
     */
    boolean deleteInstance(int instanceID) throws ServiceException;

    /**
     * Returns instance's data
     * @param instanceID ID of the instance whose data need to get
     * @return instance's data
     * @throws ServiceException if a data processing error occurs
     */
    BookInstance getBookInstance(int instanceID) throws ServiceException;

    /**
     * Returns list with the instances' data
     * @param bookID ID of the book whose instance list is returned
     * @return list with the instances' data
     * @throws ServiceException if a data processing error occurs
     */
    List<BookInstance> getBookInstances(int bookID) throws ServiceException;
}
