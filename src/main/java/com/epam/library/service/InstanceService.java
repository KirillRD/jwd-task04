package com.epam.library.service;

import com.epam.library.entity.instance.BookInstance;
import com.epam.library.entity.instance.InstanceInfo;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface InstanceService {
    void addInstance(InstanceInfo instance) throws ServiceException;
    void updateInstance(InstanceInfo instance) throws ServiceException;
    boolean deleteInstance(int instanceID) throws ServiceException;
    BookInstance getBookInstance(int instanceID) throws ServiceException;
    List<BookInstance> getBookInstances(int bookID) throws ServiceException;
}
