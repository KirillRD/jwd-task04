package com.epam.library.service;

import com.epam.library.entity.book.catalog.BookCatalog;
import com.epam.library.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Book catalog service interface
 */
public interface BookCatalogService {
    /**
     * Returns book's data
     * @param bookID ID of the book whose data need to get
     * @return book's data
     * @throws ServiceException if a data processing error occurs
     */
    BookCatalog getBookCatalog(int bookID) throws ServiceException;

    /**
     * Returns list with new books' data
     * @return list with new books' data
     * @throws ServiceException if a data processing error occurs
     */
    List<BookCatalog> getNewBookCatalogList() throws ServiceException;

    /**
     * Returns list with popular books' data
     * @return list with popular books' data
     * @throws ServiceException if a data processing error occurs
     */
    List<BookCatalog> getPopularBookCatalogList() throws ServiceException;

    /**
     * Returns the number of pages in the filtered book list
     * @param filters book filtering options
     * @return the number of pages in the filtered book list
     * @throws ServiceException if a data processing error occurs
     */
    int getPagesCount(Map<String, Object> filters) throws ServiceException;

    /**
     * Returns filtered list with books' data
     * @param filters book filtering options
     * @param page page number
     * @return filtered list with books' data
     * @throws ServiceException if a data processing error occurs
     */
    List<BookCatalog> getBookCatalogByFilter(Map<String, Object> filters, int page) throws ServiceException;
}
