package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.InstanceDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Instance;
import com.epam.library.entity.instance.BookInstance;
import com.epam.library.service.InstanceService;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public class InstanceServiceImpl implements InstanceService {

    private final InstanceDAO instanceDAO;

    public InstanceServiceImpl() {
        instanceDAO = DAOProvider.getInstance().getInstanceDAO();
    }

    @Override
    public void addInstance(Instance instance) throws ServiceException {
        try {
            instanceDAO.addInstance(instance);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateInstance(Instance instance) throws ServiceException {
        try {
            instanceDAO.updateInstance(instance);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteInstance(int instanceID) throws ServiceException {
        try {
            instanceDAO.deleteInstance(instanceID);
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
