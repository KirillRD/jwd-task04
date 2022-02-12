package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.dictionary.Publisher;

import java.util.List;

/**
 * Publisher DAO interface
 */
public interface PublisherDAO {
    /**
     * Returns true if DB contains the specified publisher
     * @param publisher whose presence is to be checked
     * @return true if DB contains the specified publisher
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean publisherExists(Publisher publisher) throws DAOException;

    /**
     * Adding a publisher
     * @param publisher to be added
     * @throws DAOException if an error occurred while accessing the database
     */
    void addPublisher(Publisher publisher) throws DAOException;

    /**
     * Returns publisher's data
     * @param publisherID ID of the publisher whose data need to get
     * @return publisher's data
     * @throws DAOException if an error occurred while accessing the database
     */
    Publisher getPublisher(int publisherID) throws DAOException;

    /**
     * Updating the publisher's data
     * @param publisher whose data needs to be updated
     * @throws DAOException if an error occurred while accessing the database
     */
    void updatePublisher(Publisher publisher) throws DAOException;

    /**
     * Returns true if publisher was deleted successfully
     * @param publisherID ID of the publisher to be deleted
     * @return true if publisher was deleted successfully
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean deletePublisher(int publisherID) throws DAOException;

    /**
     * Returns list with the publishers' data
     * @return list with the publishers' data
     * @throws DAOException if an error occurred while accessing the database
     */
    List<Publisher> getPublishersList() throws DAOException;
}
