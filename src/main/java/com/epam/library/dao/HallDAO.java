package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.instance.hall.Hall;

import java.util.List;

/**
 * Hall DAO interface
 */
public interface HallDAO {
    /**
     * Returns list with the halls' data
     * @return list with the halls' data
     * @throws DAOException if an error occurred while accessing the database
     */
    List<Hall> getHallsList() throws DAOException;
}
