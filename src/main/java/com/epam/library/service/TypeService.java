package com.epam.library.service;

import com.epam.library.entity.book.dictionary.Type;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface TypeService {
    List<Type> getTypesList() throws ServiceException;
    void addType(Type type) throws ServiceException;
    void updateType(Type type) throws ServiceException;
    Type getType(int typeID) throws ServiceException;
    boolean deleteType(int typeID) throws ServiceException;
}
