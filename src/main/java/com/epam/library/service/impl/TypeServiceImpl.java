package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.TypeDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Type;
import com.epam.library.service.TypeService;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public class TypeServiceImpl implements TypeService {
    private static final TypeDAO typeDAO = DAOProvider.getInstance().getTypeDAO();

    public TypeServiceImpl() {}

    @Override
    public List<Type> getTypesList() throws ServiceException {
        try {
            return typeDAO.getTypesList();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
