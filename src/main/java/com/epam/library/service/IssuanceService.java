package com.epam.library.service;

import com.epam.library.entity.Issuance;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface IssuanceService {
    String addIssuance(List<Issuance> issues) throws ServiceException;
    boolean updateConditionIssuance(List<String> issues, String operation) throws ServiceException;
}
