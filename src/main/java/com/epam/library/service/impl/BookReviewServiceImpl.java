package com.epam.library.service.impl;

import com.epam.library.dao.BookReviewDAO;
import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.catalog.BookReview;
import com.epam.library.service.BookReviewService;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public class BookReviewServiceImpl implements BookReviewService {
    private static final BookReviewDAO bookReviewDAO = DAOProvider.getInstance().getBookReviewDAO();

    public BookReviewServiceImpl() {}

    @Override
    public List<BookReview> getBookReviews(int bookID) throws ServiceException {
        try {
            return bookReviewDAO.getBookReviews(bookID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
