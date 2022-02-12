package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Issuance;

import java.util.List;

/**
 * Issuance DAO interface
 */
public interface IssuanceDAO {
    /**
     * Returns string of books that were not issued
     * @param issues list of books to be issued
     * @return string of books that were not issued
     * @throws DAOException if an error occurred while accessing the database
     */
    String addIssuance(List<Issuance> issues) throws DAOException;

    /**
     * Updating issuance condition
     * @param issues list of issued books to be updated
     * @param operation update operation
     * @throws DAOException if an error occurred while accessing the database
     */
    void updateConditionIssuance(List<String> issues, String operation) throws DAOException;
}
