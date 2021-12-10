package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Type;

import java.util.List;

public interface TypeDAO {
    void addType(Type type) throws DAOException;
    Type getType(int typeID) throws DAOException;
    void updateType(Type type) throws DAOException;
    void deleteType(Type type) throws DAOException;
    List<Type> getTypesList() throws DAOException;
}
