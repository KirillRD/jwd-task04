package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.entity.book.BookInfo;
import com.epam.library.service.BookService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.BookException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.book.*;
import com.epam.library.service.exception.book.empty.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AddEditBook implements Command {

    private static final Logger logger = Logger.getLogger(AddEditBook.class.getName());

    private static final String BOOK = "book";
    private static final String BOOK_ID = "book_id";
    private static final String NAME = "name";
    private static final String PUBLISHER = "publisher";
    private static final String TYPE = "type";
    private static final String PUBLICATION_YEAR = "publication_year";
    private static final String PAGES = "pages";
    private static final String PART = "part";
    private static final String ISBN = "isbn";
    private static final String ISSN = "issn";
    private static final String PRICE = "price";
    private static final String ANNOTATION = "annotation";
    private static final String GENRES = "genres";
    private static final String AUTHORS = "authors";
    private static final String IMAGE = "image";//TODO image

    private static final String REDIRECT_BOOK_ID = "&book_id=";
    private static final String MESSAGES = "messages";
    private static final Map<String, String> exceptionMessages = Map.ofEntries(
            Map.entry(EmptyAuthorException.class.getSimpleName(), "error-empty-author"),
            Map.entry(EmptyBookNameException.class.getSimpleName(), "error-empty-book-name"),
            Map.entry(EmptyGenreException.class.getSimpleName(), "error-empty-genre"),
            Map.entry(EmptyPagesException.class.getSimpleName(), "error-empty-pages"),
            Map.entry(EmptyPriceException.class.getSimpleName(), "error-empty-price"),
            Map.entry(EmptyPublicationYearException.class.getSimpleName(), "error-empty-publication-year"),
            Map.entry(EmptyPublisherException.class.getSimpleName(), "error-empty-publisher"),
            Map.entry(EmptyStandardNumberException.class.getSimpleName(), "error-empty-standard-number"),
            Map.entry(EmptyTypeException.class.getSimpleName(), "error-empty-type"),
            Map.entry(ExistStandardNumberException.class.getSimpleName(), "error-exist-standard-number"),
            Map.entry(InvalidAuthorFormatException.class.getSimpleName(), "error-author-format"),
            Map.entry(InvalidGenreFormatException.class.getSimpleName(), "error-genre-format"),
            Map.entry(InvalidISBNFormatException.class.getSimpleName(), "error-isbn-format"),
            Map.entry(InvalidISSNFormatException.class.getSimpleName(), "error-issn-format"),
            Map.entry(InvalidLengthBookNameException.class.getSimpleName(), "error-length-book-name"),
            Map.entry(InvalidPublicationYearFormatException.class.getSimpleName(), "error-publication-year-format"),
            Map.entry(InvalidPublisherFormatException.class.getSimpleName(), "error-publisher-format"),
            Map.entry(InvalidTypeFormatException.class.getSimpleName(), "error-type-format"),
            Map.entry(InvalidPagesFormatException.class.getSimpleName(), "error-pages-format"),
            Map.entry(InvalidPartFormatException.class.getSimpleName(), "error-part-format"),
            Map.entry(InvalidPriceFormatException.class.getSimpleName(), "error-price-format"),
            Map.entry(InvalidLengthAnnotationException.class.getSimpleName(), "error-length-annotation")
    );

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookInfo book = new BookInfo();
        if (request.getParameter(BOOK_ID) != null) {
            logger.info("");
            book.setId(Integer.parseInt(request.getParameter(BOOK_ID)));
        }
        book.setName(request.getParameter(NAME).trim());
        book.setPublisherID(request.getParameter(PUBLISHER));
        book.setTypeID(request.getParameter(TYPE));
        book.setPublicationYear(request.getParameter(PUBLICATION_YEAR).trim());
        book.setPages(request.getParameter(PAGES).trim());
        book.setPart(request.getParameter(PART).trim());
        book.setIsbn(request.getParameter(ISBN).trim());
        book.setIssn(request.getParameter(ISSN).trim());
        book.setPrice(request.getParameter(PRICE).trim());
        book.setAnnotation(request.getParameter(ANNOTATION).trim());
        if (request.getParameterValues(AUTHORS) != null) {
            book.setAuthorsID(Arrays.asList(request.getParameterValues(AUTHORS)));
        }
        if (request.getParameterValues(GENRES) != null) {
            book.setGenresID(Arrays.asList(request.getParameterValues(GENRES)));
        }

        BookService bookService = ServiceProvider.getInstance().getBookService();
        try {
            if (book.getId() != 0) {
                book.setImageURL(bookService.getBook(book.getId()).getImageURL());//TODO image
                bookService.updateBook(book);
            } else {
                bookService.addBook(book);
            }

            RequestProvider.redirect(RedirectCommand.BOOK_LIST_PAGE, request, response);
        } catch (BookException e) {
            HttpSession session = request.getSession();
            session.setAttribute(BOOK, book);
            session.setAttribute(MESSAGES, errorMessageBuilder(e));
            if (book.getId() != 0) {
                RequestProvider.redirect(String.format(RedirectCommand.ADD_EDIT_BOOK_PAGE, REDIRECT_BOOK_ID + book.getId()), request, response);
            } else {
                RequestProvider.redirect(String.format(RedirectCommand.ADD_EDIT_BOOK_PAGE, ""), request, response);
            }
        } catch (ServiceException e) {
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }

    private List<String> errorMessageBuilder(BookException exception) {
        List<String> messages = new ArrayList<>();
        for (BookException e : exception.getExceptions()) {
            messages.add(exceptionMessages.get(e.getClass().getSimpleName()));
        }
        return messages;
    }
}
