package com.epam.library.service;

import com.epam.library.entity.Issuance;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface IssuanceService {
    String addIssuance(List<Issuance> issuances) throws ServiceException;
    void updateReturnIssuance(int issuanceID) throws ServiceException;
    void updateExtendIssuance(int issuanceID) throws ServiceException;
    void updateLostIssuance(int issuanceID) throws ServiceException;
}
