package com.epam.library.service;

import com.epam.library.entity.Review;
import com.epam.library.entity.review.BookReview;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

/**
 * Review service interface
 */
public interface ReviewService {
    /**
     * Returns true if adding a review of the book was successful
     * @param review to be added
     * @param rating to be added
     * @return true if adding a review of the book was successful
     * @throws ServiceException if a data processing error occurs
     */
    boolean addReview(Review review, String rating) throws ServiceException;

    /**
     * Returns list with the reviews' data
     * @param bookID ID of the book whose review list is returned
     * @return list with the reviews' data
     * @throws ServiceException if a data processing error occurs
     */
    List<BookReview> getBookReviews(int bookID) throws ServiceException;
}
