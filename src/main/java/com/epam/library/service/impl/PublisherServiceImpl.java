package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.PublisherDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.dictionary.Publisher;
import com.epam.library.service.PublisherService;
import com.epam.library.service.exception.GeneralException;
import com.epam.library.service.exception.validation.PublisherValidationException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.validation.publisher.*;
import com.epam.library.service.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class PublisherServiceImpl implements PublisherService {
    private final PublisherDAO publisherDAO = DAOProvider.getInstance().getPublisherDAO();
    private static final Validator validator = Validator.getInstance();

    private static final int NAME_LENGTH = 50;
    private static final int CITY_LENGTH = 30;

    public PublisherServiceImpl() {}

    @Override
    public List<Publisher> getPublishersList() throws ServiceException {
        try {
            return publisherDAO.getPublishersList();
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public void addPublisher(Publisher publisher) throws ServiceException {

        List<PublisherValidationException> exceptions = new ArrayList<>();
        try {
            if (publisherDAO.publisherExists(publisher)) {
                exceptions.add(new ExistPublisherException());
            }

            if (validator.isEmpty(publisher.getName())) {
                exceptions.add(new EmptyPublisherNameException());
            } else if (publisher.getName().length() > NAME_LENGTH) {
                exceptions.add(new InvalidLengthPublisherNameException());
            }

            if (validator.isEmpty(publisher.getCity())) {
                exceptions.add(new EmptyPublisherCityException());
            } else if (publisher.getCity().length() > CITY_LENGTH) {
                exceptions.add(new InvalidLengthPublisherCityException());
            }

            if (exceptions.isEmpty()) {
                publisherDAO.addPublisher(publisher);
            } else {
                throw new PublisherValidationException(exceptions);
            }
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public void updatePublisher(Publisher publisher) throws ServiceException {

        List<PublisherValidationException> exceptions = new ArrayList<>();
        try {
            if (publisherDAO.publisherExists(publisher)) {
                exceptions.add(new ExistPublisherException());
            }

            if (validator.isEmpty(publisher.getName())) {
                exceptions.add(new EmptyPublisherNameException());
            } else if (publisher.getName().length() > NAME_LENGTH) {
                exceptions.add(new InvalidLengthPublisherNameException());
            }

            if (validator.isEmpty(publisher.getCity())) {
                exceptions.add(new EmptyPublisherCityException());
            } else if (publisher.getCity().length() > CITY_LENGTH) {
                exceptions.add(new InvalidLengthPublisherCityException());
            }

            if (exceptions.isEmpty()) {
                publisherDAO.updatePublisher(publisher);
            } else {
                throw new PublisherValidationException(exceptions);
            }
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public Publisher getPublisher(int publisherID) throws ServiceException {
        try {
            return publisherDAO.getPublisher(publisherID);
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public boolean deletePublisher(int publisherID) throws ServiceException {
        try {
            return publisherDAO.deletePublisher(publisherID);
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }
}
