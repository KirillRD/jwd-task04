package com.epam.library.service;

import com.epam.library.entity.book.Type;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface TypeService {
    List<Type> getTypesList() throws ServiceException;
}
