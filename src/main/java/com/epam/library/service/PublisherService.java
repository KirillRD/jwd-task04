package com.epam.library.service;

import com.epam.library.entity.book.dictionary.Publisher;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface PublisherService {
    List<Publisher> getPublishersList() throws ServiceException;
    void addPublisher(Publisher publisher) throws ServiceException;
    void updatePublisher(Publisher publisher) throws ServiceException;
    Publisher getPublisher(int publisherID) throws ServiceException;
    boolean deletePublisher(int publisherID) throws ServiceException;
}
