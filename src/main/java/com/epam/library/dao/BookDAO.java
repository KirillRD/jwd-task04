package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Book;
import com.epam.library.entity.book.BookInfo;

/**
 * Book DAO interface
 */
public interface BookDAO {
    /**
     * Returns true if DB contains book with the specified ISBN and ISSN
     * @param bookID ID of the book whose presence is to be checked
     * @param isbn ISBN of the book whose presence is to be checked
     * @param issn ISSN of the book whose presence is to be checked
     * @return true if DB contains contains book with the specified ISBN and ISSN
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean standardNumberExists(int bookID, String isbn, String issn) throws DAOException;

    /**
     * Adding a book
     * @param book to be added
     * @throws DAOException if an error occurred while accessing the database
     */
    void addBook(BookInfo book) throws DAOException;

    /**
     * Returns book's data
     * @param bookID ID of the book whose data need to get
     * @return book's data
     * @throws DAOException if an error occurred while accessing the database
     */
    Book getBook(int bookID) throws DAOException;

    /**
     * Updating the book's data
     * @param book whose data needs to be updated
     * @throws DAOException if an error occurred while accessing the database
     */
    void updateBook(BookInfo book) throws DAOException;

    /**
     * Updating the book's image
     * @param bookID ID of the book whose image needs to be updated
     * @param imageURL book image
     * @throws DAOException if an error occurred while accessing the database
     */
    void updateBookImage(int bookID, String imageURL) throws DAOException;

    /**
     * Returns true if book was deleted successfully
     * @param bookID ID of the book to be deleted
     * @return true if book was deleted successfully
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean deleteBook(int bookID) throws DAOException;
}
