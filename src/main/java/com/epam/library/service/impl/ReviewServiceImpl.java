package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.ReviewDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Review;
import com.epam.library.service.ReviewService;
import com.epam.library.service.exception.ServiceException;

public class ReviewServiceImpl implements ReviewService {

    private final ReviewDAO reviewDAO;

    public ReviewServiceImpl() {
        reviewDAO = DAOProvider.getInstance().getReviewDAO();
    }

    @Override
    public void addReview(Review review) throws ServiceException {
        try {
            reviewDAO.addReview(review);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
