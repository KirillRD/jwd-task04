package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.command.util.Util;
import com.epam.library.entity.Book;
import com.epam.library.entity.book.Author;
import com.epam.library.entity.book.Genre;
import com.epam.library.entity.book.Publisher;
import com.epam.library.entity.book.Type;
import com.epam.library.service.*;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class GoToAddEditBookPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToAddEditBookPage.class.getName());
    private LogMessageBuilder logMesBuilder;

    private static final String BOOK_ID = "book_id";
    private static final String BOOK = "book";

    private static final String PUBLISHERS = "publishers";
    private static final String TYPES = "types";
    private static final String AUTHORS = "authors";
    private static final String GENRES = "genres";
    private static final String SPACE = " ";
    private static final String POINT = ".";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logMesBuilder = new LogMessageBuilder(request);
        logger.info(logMesBuilder.build("Going to page for adding/updating book started"));

        PublisherService publisherService = ServiceProvider.getInstance().getPublisherService();
        TypeService typeService = ServiceProvider.getInstance().getTypeService();
        AuthorService authorService = ServiceProvider.getInstance().getAuthorService();
        GenreService genreService = ServiceProvider.getInstance().getGenreService();

        List<Publisher> publishers;
        List<Type> types;
        List<Author> authors;
        List<Genre> genres;

        BookService bookService = ServiceProvider.getInstance().getBookService();
        try {

            publishers = publisherService.getPublishersList();
            types = typeService.getTypesList();
            authors = authorService.getAuthorsList();
            genres = genreService.getGenresList();

            for (Author author : authors) {
                author.setFirstName(SPACE + author.getFirstName().charAt(0) + POINT);
                if (!author.getFatherName().isEmpty()) {
                    author.setFatherName(author.getFatherName().charAt(0) + POINT);
                }
            }

            request.setAttribute(PUBLISHERS, publishers);
            request.setAttribute(TYPES, types);
            request.setAttribute(AUTHORS, authors);
            request.setAttribute(GENRES, genres);


            if (request.getParameter(BOOK_ID) == null) {
                logger.info(logMesBuilder.build("Going to page for adding book was completed"));
            } else if (Util.isID(request.getParameter(BOOK_ID))) {
                int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
                Book book = bookService.getBook(bookID);
                if (book == null) {
                    logger.error(logMesBuilder.build("Invalid page attributes. Going to page for updating book is failed"));
                    RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                    return;
                }
                HttpSession session = request.getSession();
                if (session.getAttribute(BOOK) == null) {
                    session.setAttribute(BOOK, book);
                }
                logger.info(logMesBuilder.build("Going to page for updating book was completed"));
            } else {
                logger.error(logMesBuilder.build("Invalid page attributes. Going to page for adding/updating book is failed"));
                RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }

            RequestProvider.forward(PagePath.ADD_EDIT_BOOK_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error in data while going to page for adding/updating book"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
