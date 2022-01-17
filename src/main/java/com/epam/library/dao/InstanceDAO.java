package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Instance;
import com.epam.library.entity.instance.BookInstance;

import java.util.List;

public interface InstanceDAO {
    boolean addInstance(Instance instance) throws DAOException;
    Instance getInstance(int instanceID) throws DAOException;
    boolean updateInstance(Instance instance) throws DAOException;
    boolean deleteInstance(int instanceID) throws DAOException;
    BookInstance getBookInstance(int instanceID) throws DAOException;
    List<BookInstance> getBookInstances(int bookID) throws DAOException;
    List<Instance> getInstancesByCriteria() throws DAOException;
}
