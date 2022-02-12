package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Review;
import com.epam.library.entity.review.BookReview;

import java.util.List;

/**
 * Review DAO interface
 */
public interface ReviewDAO {
    /**
     * Returns true if DB contains review with the specified reader and book
     * @param bookID ID of the book whose presence is to be checked
     * @param readerID ID of the reader whose presence is to be checked
     * @return true if DB contains review with the specified reader and book
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean reviewExists(int bookID, int readerID) throws DAOException;

    /**
     * Adding a review
     * @param review to be added
     * @throws DAOException if an error occurred while accessing the database
     */
    void addReview(Review review) throws DAOException;

    /**
     * Returns list with the reviews' data
     * @param bookID ID of the book whose review list is returned
     * @return list with the reviews' data
     * @throws DAOException if an error occurred while accessing the database
     */
    List<BookReview> getBookReviews(int bookID) throws DAOException;
}
