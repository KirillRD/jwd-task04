package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.TypeDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Type;
import com.epam.library.service.TypeService;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.TypeException;
import com.epam.library.service.exception.type.EmptyTypeNameException;
import com.epam.library.service.exception.type.ExistTypeException;
import com.epam.library.service.exception.type.InvalidLengthTypeNameException;
import com.epam.library.service.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class TypeServiceImpl implements TypeService {
    private static final TypeDAO typeDAO = DAOProvider.getInstance().getTypeDAO();
    private static final Validator validator = Validator.getInstance();

    private static final int NAME_LENGTH = 25;

    public TypeServiceImpl() {}

    @Override
    public List<Type> getTypesList() throws ServiceException {
        try {
            return typeDAO.getTypesList();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addType(Type type) throws ServiceException {

        List<TypeException> exceptions = new ArrayList<>();
        try {
            if (!typeDAO.checkType(type)) {
                exceptions.add(new ExistTypeException());
            }

            if (validator.isEmpty(type.getName())) {
                exceptions.add(new EmptyTypeNameException());
            } else if (type.getName().length() > NAME_LENGTH) {
                exceptions.add(new InvalidLengthTypeNameException());
            }

            if (exceptions.isEmpty()) {
                typeDAO.addType(type);
            } else {
                throw new TypeException(exceptions);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateType(Type type) throws ServiceException {

        List<TypeException> exceptions = new ArrayList<>();
        try {
            if (!typeDAO.checkType(type)) {
                exceptions.add(new ExistTypeException());
            }

            if (validator.isEmpty(type.getName())) {
                exceptions.add(new EmptyTypeNameException());
            } else if (type.getName().length() > NAME_LENGTH) {
                exceptions.add(new InvalidLengthTypeNameException());
            }

            if (exceptions.isEmpty()) {
                typeDAO.updateType(type);
            } else {
                throw new TypeException(exceptions);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Type getType(int typeID) throws ServiceException {
        try {
            return typeDAO.getType(typeID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteType(int typeID) throws ServiceException {
        try {
            return typeDAO.deleteType(typeID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
