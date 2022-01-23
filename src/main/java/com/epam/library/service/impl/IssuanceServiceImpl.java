package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.IssuanceDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Issuance;
import com.epam.library.constant.IssuanceOperation;
import com.epam.library.service.IssuanceService;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.validation.Validator;

import java.util.List;

public class IssuanceServiceImpl implements IssuanceService {
    private static final IssuanceDAO issuanceDAO = DAOProvider.getInstance().getIssuanceDAO();
    private static final Validator validator = Validator.getInstance();

    public IssuanceServiceImpl() {}

    @Override
    public String addIssuance(List<Issuance> issues) throws ServiceException {
        try {
            return issuanceDAO.addIssuance(issues);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateConditionIssuance(List<String> issues, String operation) throws ServiceException {
        try {
            if (!IssuanceOperation.containsOperation(operation)) {
                return false;
            }

            for (String issuanceID : issues) {
                if (!validator.isInteger(issuanceID)) {
                    return false;
                }
            }

            issuanceDAO.updateConditionIssuance(issues, operation);
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
