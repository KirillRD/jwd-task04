package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.catalog.BookReview;

import java.util.List;

public interface BookReviewDAO {
    List<BookReview> getBookReviews(int bookID) throws DAOException;
}
