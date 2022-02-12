package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.dictionary.Author;

import java.util.List;

/**
 * Author DAO interface
 */
public interface AuthorDAO {
    /**
     * Returns true if DB contains the specified author
     * @param author whose presence is to be checked
     * @return true if DB contains the specified author
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean authorExists(Author author) throws DAOException;

    /**
     * Adding an author
     * @param author to be added
     * @throws DAOException if an error occurred while accessing the database
     */
    void addAuthor(Author author) throws DAOException;

    /**
     * Returns author's data
     * @param authorID ID of the author whose data need to get
     * @return author's data
     * @throws DAOException if an error occurred while accessing the database
     */
    Author getAuthor(int authorID) throws DAOException;

    /**
     * Updating the author's data
     * @param author whose data needs to be updated
     * @throws DAOException if an error occurred while accessing the database
     */
    void updateAuthor(Author author) throws DAOException;

    /**
     * Returns true if author was deleted successfully
     * @param authorID ID of the author to be deleted
     * @return true if author was deleted successfully
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean deleteAuthor(int authorID) throws DAOException;

    /**
     * Returns list with the authors' data
     * @return list with the authors' data
     * @throws DAOException if an error occurred while accessing the database
     */
    List<Author> getAuthorsList() throws DAOException;
}
