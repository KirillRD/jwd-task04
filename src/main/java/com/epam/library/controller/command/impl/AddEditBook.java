package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.entity.Book;
import com.epam.library.service.BookService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AddEditBook implements Command {

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
    private static final String IMAGE = "passport";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Book book = new Book();
        if (request.getParameter(BOOK_ID) != null) {
            book.setId(Integer.parseInt(request.getParameter(BOOK_ID)));
        }
        book.setName(request.getParameter(NAME).trim());
        book.setPublisherID(Integer.parseInt(request.getParameter(PUBLISHER)));
        book.setTypeID(Integer.parseInt(request.getParameter(TYPE)));
        book.setPublicationYear(Integer.parseInt(request.getParameter(PUBLICATION_YEAR).trim()));
        book.setPages(Integer.parseInt(request.getParameter(PAGES).trim()));
        book.setPart(Integer.parseInt(request.getParameter(PART).trim()));
        book.setIsbn(request.getParameter(ISBN).trim());
        book.setIssn(request.getParameter(ISSN).trim());
        book.setPrice(Double.parseDouble(request.getParameter(PRICE).trim()));
        book.setAnnotation(request.getParameter(ANNOTATION).trim());

        for (String genre : request.getParameterValues(GENRES)) {
            book.getGenresID().add(Integer.parseInt(genre));
        }

        for (String author : request.getParameterValues(AUTHORS)) {
            book.getAuthorsID().add(Integer.parseInt(author));
        }

        BookService bookService = ServiceProvider.getInstance().getBookService();
        try {
            if (book.getId() != 0) {
                bookService.updateBook(book);
            } else {
                bookService.addBook(book);
            }
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.redirect(RedirectCommand.BOOK_LIST_PAGE, request, response);
    }
}
