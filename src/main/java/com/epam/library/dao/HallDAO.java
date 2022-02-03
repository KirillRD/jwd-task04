package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.instance.hall.Hall;

import java.util.List;

public interface HallDAO {
    List<Hall> getHallsList() throws DAOException;
}
