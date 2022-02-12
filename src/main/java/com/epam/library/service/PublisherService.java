package com.epam.library.service;

import com.epam.library.entity.book.dictionary.Publisher;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

/**
 * Publisher service interface
 */
public interface PublisherService {
    /**
     * Returns list with the publishers' data
     * @return list with the publishers' data
     * @throws ServiceException if a data processing error occurs
     */
    List<Publisher> getPublishersList() throws ServiceException;

    /**
     * Adding a publisher
     * @param publisher to be added
     * @throws ServiceException if a data processing error occurs
     */
    void addPublisher(Publisher publisher) throws ServiceException;

    /**
     * Updating the publisher's data
     * @param publisher whose data needs to be updated
     * @throws ServiceException if a data processing error occurs
     */
    void updatePublisher(Publisher publisher) throws ServiceException;

    /**
     * Returns publisher's data
     * @param publisherID ID of the publisher whose data need to get
     * @return publisher's data
     * @throws ServiceException if a data processing error occurs
     */
    Publisher getPublisher(int publisherID) throws ServiceException;

    /**
     * Returns true if publisher was deleted successfully
     * @param publisherID ID of the publisher to be deleted
     * @return true if publisher was deleted successfully
     * @throws ServiceException if a data processing error occurs
     */
    boolean deletePublisher(int publisherID) throws ServiceException;
}
