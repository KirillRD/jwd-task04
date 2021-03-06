package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.InstanceDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.instance.BookInstance;
import com.epam.library.entity.Instance;
import com.epam.library.service.InstanceService;
import com.epam.library.service.exception.GeneralException;
import com.epam.library.service.exception.validation.InstanceValidationException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.validation.instance.*;
import com.epam.library.service.validation.Validator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class InstanceServiceImpl implements InstanceService {
    private static final InstanceDAO instanceDAO = DAOProvider.getInstance().getInstanceDAO();
    private static final Validator validator = Validator.getInstance();

    private static final int NUMBER_LENGTH = 10;

    public InstanceServiceImpl() {}

    @Override
    public void addInstance(Instance instance) throws ServiceException {

        List<InstanceValidationException> exceptions = new ArrayList<>();
        try {
            if (validator.isEmpty(instance.getNumber())) {
                exceptions.add(new EmptyNumberException());
            } else if (instance.getNumber().length() > NUMBER_LENGTH) {
                exceptions.add(new InvalidLengthNumberException());
            } else if (instanceDAO.instanceNumberExists(instance.getId(), instance.getNumber())) {
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
            } else if (!validator.isEmpty(instance.getWriteOffDate()) && Date.valueOf(instance.getWriteOffDate()).compareTo(Date.valueOf(instance.getReceivedDate())) < 0) {
                exceptions.add(new InvalidWriteOffDateException());
            }

            if (exceptions.isEmpty()) {
                instanceDAO.addInstance(instance);
            } else {
                throw new InstanceValidationException(exceptions);
            }
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public void updateInstance(Instance instance) throws ServiceException {

        List<InstanceValidationException> exceptions = new ArrayList<>();
        try {
            if (validator.isEmpty(instance.getNumber())) {
                exceptions.add(new EmptyNumberException());
            } else if (instance.getNumber().length() > NUMBER_LENGTH) {
                exceptions.add(new InvalidLengthNumberException());
            } else if (instanceDAO.instanceNumberExists(instance.getId(), instance.getNumber())) {
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
            } else if (!validator.isEmpty(instance.getWriteOffDate()) && Date.valueOf(instance.getWriteOffDate()).compareTo(Date.valueOf(instance.getReceivedDate())) < 0) {
                exceptions.add(new InvalidWriteOffDateException());
            }

            if (exceptions.isEmpty()) {
                instanceDAO.updateInstance(instance);
            } else {
                throw new InstanceValidationException(exceptions);
            }
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public boolean deleteInstance(int instanceID) throws ServiceException {
        try {
            return instanceDAO.deleteInstance(instanceID);
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public BookInstance getBookInstance(int instanceID) throws ServiceException {
        try {
            return instanceDAO.getBookInstance(instanceID);
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public List<BookInstance> getBookInstances(int bookID) throws ServiceException {
        try {
            return instanceDAO.getBookInstances(bookID);
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }
}
