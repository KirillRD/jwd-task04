package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Instance;
import com.epam.library.entity.instance.BookInstance;
import com.epam.library.entity.instance.InstanceInfo;

import java.util.List;

public interface InstanceDAO {
    boolean checkInstanceNumber(int instanceID, String number) throws DAOException;
    void addInstance(InstanceInfo instance) throws DAOException;
    Instance getInstance(int instanceID) throws DAOException;
    void updateInstance(InstanceInfo instance) throws DAOException;
    boolean deleteInstance(int instanceID) throws DAOException;
    BookInstance getBookInstance(int instanceID) throws DAOException;
    List<BookInstance> getBookInstances(int bookID) throws DAOException;
}
