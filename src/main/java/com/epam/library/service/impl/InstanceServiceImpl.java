package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.InstanceDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.instance.BookInstance;
import com.epam.library.entity.instance.InstanceInfo;
import com.epam.library.service.InstanceService;
import com.epam.library.service.exception.InstanceException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.instance.*;
import com.epam.library.service.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class InstanceServiceImpl implements InstanceService {
    private static final InstanceDAO instanceDAO = DAOProvider.getInstance().getInstanceDAO();
    private static final Validator validator = Validator.getInstance();

    private static final int NUMBER_LENGTH = 10;

    public InstanceServiceImpl() {}

    @Override
    public void addInstance(InstanceInfo instance) throws ServiceException {

        List<InstanceException> exceptions = new ArrayList<>();
        try {
            if (validator.isEmpty(instance.getNumber())) {
                exceptions.add(new EmptyNumberException());
            } else if (instance.getNumber().length() > NUMBER_LENGTH) {
                exceptions.add(new InvalidLengthNumberException());
            } else if (!instanceDAO.checkInstanceNumber(instance.getId(), instance.getNumber())) {
                exceptions.add(new ExistNumberException());
            }

            if (validator.isEmpty(instance.getHallID())) {
                exceptions.add(new EmptyHallException());
            } else if (!validator.isInteger(instance.getHallID())) {
                exceptions.add(new InvalidHallFormatException());
            }

            if (validator.isEmpty(instance.getReceivedDate())) {
                exceptions.add(new EmptyReceivedDateException());
            } else if (!validator.isDate(instance.getReceivedDate())) {
                exceptions.add(new InvalidReceivedDateFormatException());
            }

            if (!validator.isEmpty(instance.getWriteOffDate()) && !validator.isDate(instance.getWriteOffDate())) {
                exceptions.add(new InvalidWriteOffDateFormatException());
            }

            if (exceptions.isEmpty()) {
                instanceDAO.addInstance(instance);
            } else {
                throw new InstanceException(exceptions);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateInstance(InstanceInfo instance) throws ServiceException {

        List<InstanceException> exceptions = new ArrayList<>();
        try {
            if (validator.isEmpty(instance.getNumber())) {
                exceptions.add(new EmptyNumberException());
            } else if (instance.getNumber().length() > NUMBER_LENGTH) {
                exceptions.add(new InvalidLengthNumberException());
            } else if (!instanceDAO.checkInstanceNumber(instance.getId(), instance.getNumber())) {
                exceptions.add(new ExistNumberException());
            }

            if (validator.isEmpty(instance.getHallID())) {
                exceptions.add(new EmptyHallException());
            } else if (!validator.isInteger(instance.getHallID())) {
                exceptions.add(new InvalidHallFormatException());
            }

            if (validator.isEmpty(instance.getReceivedDate())) {
                exceptions.add(new EmptyReceivedDateException());
            } else if (!validator.isDate(instance.getReceivedDate())) {
                exceptions.add(new InvalidReceivedDateFormatException());
            }

            if (!validator.isEmpty(instance.getWriteOffDate()) && !validator.isDate(instance.getWriteOffDate())) {
                exceptions.add(new InvalidWriteOffDateFormatException());
            }

            if (exceptions.isEmpty()) {
                instanceDAO.updateInstance(instance);
            } else {
                throw new InstanceException(exceptions);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteInstance(int instanceID) throws ServiceException {
        try {
            return instanceDAO.deleteInstance(instanceID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public BookInstance getBookInstance(int instanceID) throws ServiceException {
        try {
            return instanceDAO.getBookInstance(instanceID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BookInstance> getBookInstances(int bookID) throws ServiceException {
        try {
            return instanceDAO.getBookInstances(bookID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
