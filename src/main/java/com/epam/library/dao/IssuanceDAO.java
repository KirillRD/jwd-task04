package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Issuance;

import java.util.List;

public interface IssuanceDAO {
    String addIssuance(List<Issuance> issuances) throws DAOException;
    Issuance getIssuance(int issuanceID) throws DAOException;
    void updateIssuance(Issuance issuance) throws DAOException;
    void deleteIssuance(Issuance issuance) throws DAOException;
    void updateReturnIssuance(int issuanceID) throws DAOException;
    void updateExtendIssuance(int issuanceID) throws DAOException;
    void updateLostIssuance(int issuanceID) throws DAOException;
}
