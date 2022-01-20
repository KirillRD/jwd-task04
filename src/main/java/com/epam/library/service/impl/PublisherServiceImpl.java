package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.PublisherDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Publisher;
import com.epam.library.service.PublisherService;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public class PublisherServiceImpl implements PublisherService {
    private final PublisherDAO publisherDAO = DAOProvider.getInstance().getPublisherDAO();

    public PublisherServiceImpl() {}

    @Override
    public List<Publisher> getPublishersList() throws ServiceException {
        try {
            return publisherDAO.getPublishersList();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
