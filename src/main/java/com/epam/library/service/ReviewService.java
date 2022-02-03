package com.epam.library.service;

import com.epam.library.entity.Review;
import com.epam.library.entity.review.BookReview;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface ReviewService {
    boolean addReview(Review review, String rating) throws ServiceException;
    List<BookReview> getBookReviews(int bookID) throws ServiceException;
}
