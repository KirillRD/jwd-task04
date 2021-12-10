package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Issuance;

public interface IssuanceDAO {
    void addIssuance(Issuance issuance) throws DAOException;
    Issuance getIssuance(int issuanceID) throws DAOException;
    void updateIssuance(Issuance issuance) throws DAOException;
    void deleteIssuance(Issuance issuance) throws DAOException;
}
