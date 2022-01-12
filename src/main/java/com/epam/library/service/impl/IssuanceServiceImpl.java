package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.IssuanceDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Issuance;
import com.epam.library.service.IssuanceService;
import com.epam.library.service.exception.ServiceException;

public class IssuanceServiceImpl implements IssuanceService {

    private final IssuanceDAO issuanceDAO;

    public IssuanceServiceImpl() {
        issuanceDAO = DAOProvider.getInstance().getIssuanceDAO();
    }

    @Override
    public void addIssuance(Issuance issuance) throws ServiceException {
        try {
            issuanceDAO.addIssuance(issuance);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateReturnIssuance(int issuanceID) throws ServiceException {
        try {
            issuanceDAO.updateReturnIssuance(issuanceID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateExtendIssuance(int issuanceID) throws ServiceException {
        try {
            issuanceDAO.updateExtendIssuance(issuanceID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateLostIssuance(int issuanceID) throws ServiceException {
        try {
            issuanceDAO.updateLostIssuance(issuanceID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
