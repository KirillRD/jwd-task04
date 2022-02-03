package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.dictionary.Publisher;

import java.util.List;

public interface PublisherDAO {
    boolean checkPublisher(Publisher publisher) throws DAOException;
    void addPublisher(Publisher publisher) throws DAOException;
    Publisher getPublisher(int publisherID) throws DAOException;
    void updatePublisher(Publisher publisher) throws DAOException;
    boolean deletePublisher(int publisherID) throws DAOException;
    List<Publisher> getPublishersList() throws DAOException;
}
