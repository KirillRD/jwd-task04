package com.epam.library.service;

import com.epam.library.entity.book.catalog.BookReview;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface BookReviewService {
    List<BookReview> getBookReviews(int bookID) throws ServiceException;
}
