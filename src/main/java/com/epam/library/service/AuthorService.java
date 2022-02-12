package com.epam.library.service;

import com.epam.library.entity.book.dictionary.Author;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

/**
 * Author service interface
 */
public interface AuthorService {
    /**
     * Returns list with the authors' data
     * @return list with the authors' data
     * @throws ServiceException if a data processing error occurs
     */
    List<Author> getAuthorsList() throws ServiceException;

    /**
     * Adding an author
     * @param author to be added
     * @throws ServiceException if a data processing error occurs
     */
    void addAuthor(Author author) throws ServiceException;

    /**
     * Updating the author's data
     * @param author whose data needs to be updated
     * @throws ServiceException if a data processing error occurs
     */
    void updateAuthor(Author author) throws ServiceException;

    /**
     * Returns author's data
     * @param authorID ID of the author whose data need to get
     * @return author's data
     * @throws ServiceException if a data processing error occurs
     */
    Author getAuthor(int authorID) throws ServiceException;

    /**
     * Returns true if author was deleted successfully
     * @param authorID ID of the author to be deleted
     * @return true if author was deleted successfully
     * @throws ServiceException if a data processing error occurs
     */
    boolean deleteAuthor(int authorID) throws ServiceException;
}
