package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.dictionary.Type;

import java.util.List;

/**
 * Type DAO interface
 */
public interface TypeDAO {
    /**
     * Returns true if DB contains the specified type
     * @param type whose presence is to be checked
     * @return true if DB contains the specified type
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean typeExists(Type type) throws DAOException;

    /**
     * Adding a type
     * @param type to be added
     * @throws DAOException if an error occurred while accessing the database
     */
    void addType(Type type) throws DAOException;

    /**
     * Returns type's data
     * @param typeID ID of the type whose data need to get
     * @return type's data
     * @throws DAOException if an error occurred while accessing the database
     */
    Type getType(int typeID) throws DAOException;

    /**
     * Updating the type's data
     * @param type whose data needs to be updated
     * @throws DAOException if an error occurred while accessing the database
     */
    void updateType(Type type) throws DAOException;

    /**
     * Returns true if type was deleted successfully
     * @param typeID ID of the type to be deleted
     * @return true if type was deleted successfully
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean deleteType(int typeID) throws DAOException;

    /**
     * Returns list with the types' data
     * @return list with the types' data
     * @throws DAOException if an error occurred while accessing the database
     */
    List<Type> getTypesList() throws DAOException;
}
