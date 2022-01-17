package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Review;

import java.util.List;

public interface ReviewDAO {
    boolean addReview(Review review) throws DAOException;
    Review getReview(int reviewID) throws DAOException;
    void updateReview(Review review) throws DAOException;
    void deleteReview(Review review) throws DAOException;
    List<Review> getReviewsByBook(int bookID) throws DAOException;
}
