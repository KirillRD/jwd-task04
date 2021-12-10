package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Instance;

import java.util.List;

public interface InstanceDAO {
    void addInstance(Instance instance) throws DAOException;
    Instance getInstance(int instanceID) throws DAOException;
    void updateInstance(Instance instance) throws DAOException;
    void deleteInstance(Instance instance) throws DAOException;
    List<Instance> getInstancesByCriteria() throws DAOException;
}
