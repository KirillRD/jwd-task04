package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.entity.Review;
import com.epam.library.entity.user.SessionUser;
import com.epam.library.service.ReviewService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ReviewException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.review.EmptyRatingException;
import com.epam.library.service.exception.review.InvalidLengthCommentException;
import com.epam.library.service.exception.review.InvalidRatingFormatException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddReview implements Command {

    private static final String BOOK_ID = "book_id";
    private static final String RATING = "rating";
    private static final String COMMENT = "comment";
    private static final String MESSAGE = "message";
    private static final String ERROR_ADD_REVIEW = "error-add-review";

    private static final String MESSAGES = "messages";
    private static final Map<String, String> exceptionMessages = Map.ofEntries(
            Map.entry(EmptyRatingException.class.getSimpleName(), "error-empty-rating"),
            Map.entry(InvalidLengthCommentException.class.getSimpleName(), "error-length-comment"),
            Map.entry(InvalidRatingFormatException.class.getSimpleName(), "error-rating-format")
    );

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReviewService reviewService = ServiceProvider.getInstance().getReviewService();

        SessionUser sessionUser = SessionUserProvider.getSessionUser(request);
        int userID = sessionUser.getId();
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        Review review = new Review();
        review.setReaderID(userID);
        review.setBookID(bookID);
        String rating = request.getParameter(RATING);
        String comment = request.getParameter(COMMENT).trim();
        review.setComment(comment);
        try {
            if (!reviewService.addReview(review, rating)) {
                HttpSession session = request.getSession();
                session.setAttribute(MESSAGE, ERROR_ADD_REVIEW);
            }
            RequestProvider.redirect(String.format(RedirectCommand.BOOK_PAGE, bookID), request, response);
        } catch (ReviewException e) {
            HttpSession session = request.getSession();
            session.setAttribute(RATING, rating);
            session.setAttribute(COMMENT, comment);
            session.setAttribute(MESSAGES, errorMessageBuilder(e));
            RequestProvider.redirect(String.format(RedirectCommand.BOOK_PAGE, bookID), request, response);
        } catch (ServiceException e) {
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }

    private List<String> errorMessageBuilder(ReviewException exception) {
        List<String> messages = new ArrayList<>();
        for (ReviewException e : exception.getExceptions()) {
            messages.add(exceptionMessages.get(e.getClass().getSimpleName()));
        }
        return messages;
    }
}
