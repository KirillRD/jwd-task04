package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.Review;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.ReviewService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AddReview implements Command {

    private static final String BOOK_ID = "book_id";
    private static final String RATING = "rating";
    private static final String COMMENT = "comment";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReviewService reviewService = ServiceProvider.getInstance().getReviewService();

        SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
        int userID = sessionUser.getId();
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        try {
            Review review = new Review();
            review.setReaderID(userID);
            review.setBookID(bookID);
            int rating = Integer.parseInt(request.getParameter(RATING));
            review.setRating(rating);
            String comment = request.getParameter(COMMENT).trim();
            review.setComment(comment);
            reviewService.addReview(review);
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.redirect(String.format(RedirectCommand.BOOK_PAGE, bookID), request, response);
    }
}
