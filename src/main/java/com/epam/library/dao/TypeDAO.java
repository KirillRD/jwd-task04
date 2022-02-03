package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.dictionary.Type;

import java.util.List;

public interface TypeDAO {
    boolean checkType(Type type) throws DAOException;
    void addType(Type type) throws DAOException;
    Type getType(int typeID) throws DAOException;
    void updateType(Type type) throws DAOException;
    boolean deleteType(int typeID) throws DAOException;
    List<Type> getTypesList() throws DAOException;
}
