package com.epam.library.service;

import com.epam.library.entity.Book;
import com.epam.library.entity.book.BookInfo;
import com.epam.library.service.exception.ServiceException;

/**
 * Book service interface
 */
public interface BookService {
    /**
     * Adding a book
     * @param book to be added
     * @throws ServiceException if a data processing error occurs
     */
    void addBook (BookInfo book) throws ServiceException;

    /**
     * Returns book's data
     * @param bookID ID of the book whose data need to get
     * @return book's data
     * @throws ServiceException if a data processing error occurs
     */
    Book getBook (int bookID) throws ServiceException;

    /**
     * Updating the book's data
     * @param book whose data needs to be updated
     * @throws ServiceException if a data processing error occurs
     */
    void updateBook (BookInfo book) throws ServiceException;

    /**
     * Updating the book's image
     * @param bookID ID of the book whose image needs to be updated
     * @param imageURL book image
     * @throws ServiceException if a data processing error occurs
     */
    void updateBookImage(int bookID, String imageURL) throws ServiceException;

    /**
     * Returns true if book was deleted successfully
     * @param bookID ID of the book to be deleted
     * @return true if book was deleted successfully
     * @throws ServiceException if a data processing error occurs
     */
    boolean deleteBook (int bookID) throws ServiceException;
}
