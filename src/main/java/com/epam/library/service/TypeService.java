package com.epam.library.service;

import com.epam.library.entity.book.dictionary.Type;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

/**
 * Type service interface
 */
public interface TypeService {
    /**
     * Returns list with the types' data
     * @return list with the types' data
     * @throws ServiceException if a data processing error occurs
     */
    List<Type> getTypesList() throws ServiceException;

    /**
     * Adding a type
     * @param type to be added
     * @throws ServiceException if a data processing error occurs
     */
    void addType(Type type) throws ServiceException;

    /**
     * Updating the type's data
     * @param type whose data needs to be updated
     * @throws ServiceException if a data processing error occurs
     */
    void updateType(Type type) throws ServiceException;

    /**
     * Returns type's data
     * @param typeID ID of the type whose data need to get
     * @return type's data
     * @throws ServiceException if a data processing error occurs
     */
    Type getType(int typeID) throws ServiceException;

    /**
     * Returns true if type was deleted successfully
     * @param typeID ID of the type to be deleted
     * @return true if type was deleted successfully
     * @throws ServiceException if a data processing error occurs
     */
    boolean deleteType(int typeID) throws ServiceException;
}
