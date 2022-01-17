package com.epam.library.service;

import com.epam.library.entity.Instance;
import com.epam.library.entity.instance.BookInstance;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface InstanceService {
    boolean addInstance(Instance instance) throws ServiceException;
    boolean updateInstance(Instance instance) throws ServiceException;
    boolean deleteInstance(int instanceID) throws ServiceException;
    BookInstance getBookInstance(int instanceID) throws ServiceException;
    List<BookInstance> getBookInstances(int bookID) throws ServiceException;
}
