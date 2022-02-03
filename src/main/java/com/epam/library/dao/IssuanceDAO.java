package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Issuance;

import java.util.List;

public interface IssuanceDAO {
    String addIssuance(List<Issuance> issues) throws DAOException;
    void updateConditionIssuance(List<String> issues, String operation) throws DAOException;
}
