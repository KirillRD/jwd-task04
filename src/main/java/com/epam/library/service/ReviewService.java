package com.epam.library.service;

import com.epam.library.entity.Review;
import com.epam.library.service.exception.ServiceException;

public interface ReviewService {
    boolean addReview(Review review) throws ServiceException;
}
