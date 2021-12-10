package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.instance.Hall;

import java.util.List;

public interface HallDAO {
    void addHall(Hall hall) throws DAOException;
    Hall getHall(int hallID) throws DAOException;
    void updateHall(Hall hall) throws DAOException;
    void deleteHall(Hall hall) throws DAOException;
    List<Hall> getHallsList() throws DAOException;
}
