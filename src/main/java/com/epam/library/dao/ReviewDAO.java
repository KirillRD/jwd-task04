package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Review;

public interface ReviewDAO {
    boolean checkReview(int bookID, int readerID) throws DAOException;
    void addReview(Review review) throws DAOException;
    Review getReview(int reviewID) throws DAOException;
    void updateReview(Review review) throws DAOException;
    void deleteReview(Review review) throws DAOException;
}
