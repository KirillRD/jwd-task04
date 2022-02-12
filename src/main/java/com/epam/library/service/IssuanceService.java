package com.epam.library.service;

import com.epam.library.entity.Issuance;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

/**
 * Issuance service interface
 */
public interface IssuanceService {
    /**
     * Returns string of books that were not issued
     * @param issues list of books to be issued
     * @return string of books that were not issued
     * @throws ServiceException if a data processing error occurs
     */
    String addIssuance(List<Issuance> issues) throws ServiceException;

    /**
     * Returns true if book issuance update has been completed successfully
     * @param issues list of issued books to be updated
     * @param operation update operation
     * @return true if book issuance update has been completed successfully
     * @throws ServiceException if a data processing error occurs
     */
    boolean updateConditionIssuance(List<String> issues, String operation) throws ServiceException;
}
