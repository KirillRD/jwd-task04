package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.ReviewDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Review;
import com.epam.library.entity.review.BookReview;
import com.epam.library.service.ReviewService;
import com.epam.library.service.exception.GeneralException;
import com.epam.library.service.exception.validation.ReviewValidationException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.validation.review.*;
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

        List<ReviewValidationException> exceptions = new ArrayList<>();
        try {
            if (reviewDAO.reviewExists(review.getBookID(), review.getReaderID())) {
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
                throw new ReviewValidationException(exceptions);
            }
            return true;
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public List<BookReview> getBookReviews(int bookID) throws ServiceException {
        try {
            return reviewDAO.getBookReviews(bookID);
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }
}
