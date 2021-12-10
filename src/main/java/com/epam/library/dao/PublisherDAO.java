package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Publisher;

import java.util.List;

public interface PublisherDAO {
    void addPublisher(Publisher publisher) throws DAOException;
    Publisher getPublisher(int publisherID) throws DAOException;
    void updatePublisher(Publisher publisher) throws DAOException;
    void deletePublisher(Publisher publisher) throws DAOException;
    List<Publisher> getPublishersList() throws DAOException;
}
