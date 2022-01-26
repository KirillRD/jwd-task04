package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.ReviewDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Review;
import com.epam.library.entity.book.catalog.BookReview;
import com.epam.library.service.ReviewService;
import com.epam.library.service.exception.ReviewException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.review.EmptyRatingException;
import com.epam.library.service.exception.review.InvalidLengthCommentException;
import com.epam.library.service.exception.review.InvalidRatingFormatException;
import com.epam.library.service.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    private static final ReviewDAO reviewDAO = DAOProvider.getInstance().getReviewDAO();
    private static final Validator validator = Validator.getInstance();

    private static final int COMMENT_LENGTH = 1000;

    public ReviewServiceImpl() {}

    @Override
    public boolean addReview(Review review, String rating) throws ServiceException {

        List<ReviewException> exceptions = new ArrayList<>();
        try {
            if (!reviewDAO.checkReview(review.getBookID(), review.getReaderID())) {
                return false;
            }

            if (validator.isEmpty(rating)) {
                exceptions.add(new EmptyRatingException());
            } else if (!validator.isInteger(rating) || (validator.isInteger(rating) && Integer.parseInt(rating) > 5)) {
                exceptions.add(new InvalidRatingFormatException());
            }

            if (review.getComment().length() > COMMENT_LENGTH) {
                exceptions.add(new InvalidLengthCommentException());
            }

            if (exceptions.isEmpty()) {
                review.setRating(Integer.parseInt(rating));
                reviewDAO.addReview(review);
            } else {
                throw new ReviewException(exceptions);
            }
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BookReview> getBookReviews(int bookID) throws ServiceException {
        try {
            return reviewDAO.getBookReviews(bookID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
