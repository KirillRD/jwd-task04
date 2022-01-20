package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Issuance;

import java.util.List;

public interface IssuanceDAO {
    String addIssuance(List<Issuance> issues) throws DAOException;
    Issuance getIssuance(int issuanceID) throws DAOException;
    void updateIssuance(Issuance issuance) throws DAOException;
    void deleteIssuance(Issuance issuance) throws DAOException;
    void updateConditionIssuance(List<String> issues, String operation) throws DAOException;
}
