package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.HallDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.instance.Hall;
import com.epam.library.service.HallService;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public class HallServiceImpl implements HallService {

    private final HallDAO hallDAO;

    public HallServiceImpl() {
        hallDAO = DAOProvider.getInstance().getHallDAO();
    }

    @Override
    public List<Hall> getHallList() throws ServiceException {
        try {
            return hallDAO.getHallsList();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Hall getHall(int hallID) throws ServiceException {
        try {
            return hallDAO.getHall(hallID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
