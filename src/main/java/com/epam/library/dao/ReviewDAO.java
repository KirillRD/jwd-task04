package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Review;
import com.epam.library.entity.review.BookReview;

import java.util.List;

public interface ReviewDAO {
    boolean checkReview(int bookID, int readerID) throws DAOException;
    void addReview(Review review) throws DAOException;
    List<BookReview> getBookReviews(int bookID) throws DAOException;
}
