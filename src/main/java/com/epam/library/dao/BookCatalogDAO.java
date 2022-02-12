package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.catalog.BookCatalog;

import java.util.List;
import java.util.Map;

/**
 * Book catalog DAO interface
 */
public interface BookCatalogDAO {
    /**
     * Returns book's data
     * @param bookID ID of the book whose data need to get
     * @return book's data
     * @throws DAOException if an error occurred while accessing the database
     */
    BookCatalog getBookCatalog(int bookID) throws DAOException;

    /**
     * Returns list with new books' data
     * @return list with new books' data
     * @throws DAOException if an error occurred while accessing the database
     */
    List<BookCatalog> getNewBookCatalogList() throws DAOException;

    /**
     * Returns list with popular books' data
     * @return list with popular books' data
     * @throws DAOException if an error occurred while accessing the database
     */
    List<BookCatalog> getPopularBookCatalogList() throws DAOException;

    /**
     * Returns the number of pages in the filtered book list
     * @param filters book filtering options
     * @return the number of pages in the filtered book list
     * @throws DAOException if an error occurred while accessing the database
     */
    int getPagesCount(Map<String, Object> filters) throws DAOException;

    /**
     * Returns filtered list with books' data
     * @param filters book filtering options
     * @param page page number
     * @return filtered list with books' data
     * @throws DAOException if an error occurred while accessing the database
     */
    List<BookCatalog> getBookCatalogByFilter(Map<String, Object> filters, int page) throws DAOException;
}
