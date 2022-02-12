package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.HallDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.instance.hall.Hall;
import com.epam.library.service.HallService;
import com.epam.library.service.exception.GeneralException;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public class HallServiceImpl implements HallService {
    private static final HallDAO hallDAO = DAOProvider.getInstance().getHallDAO();

    public HallServiceImpl() {}

    @Override
    public List<Hall> getHallList() throws ServiceException {
        try {
            return hallDAO.getHallsList();
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }
}
