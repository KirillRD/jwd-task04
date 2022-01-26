package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Review;
import com.epam.library.entity.book.catalog.BookReview;

import java.util.List;

public interface ReviewDAO {
    boolean checkReview(int bookID, int readerID) throws DAOException;
    void addReview(Review review) throws DAOException;
    Review getReview(int reviewID) throws DAOException;
    void updateReview(Review review) throws DAOException;
    void deleteReview(Review review) throws DAOException;
    public List<BookReview> getBookReviews(int bookID) throws DAOException;
}
