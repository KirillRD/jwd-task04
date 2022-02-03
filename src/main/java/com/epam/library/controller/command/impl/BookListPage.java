package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.book_catalog_filter.BookCatalogFilter;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.PagePath;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.entity.book.dictionary.Author;
import com.epam.library.entity.book.dictionary.Genre;
import com.epam.library.entity.book.dictionary.Publisher;
import com.epam.library.entity.book.dictionary.Type;
import com.epam.library.entity.book.catalog.BookCatalog;
import com.epam.library.service.*;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class BookListPage implements Command {
    private static final Logger logger = Logger.getLogger(BookListPage.class.getName());

    private static final String BOOK_CATALOG = "book_catalog";
    private static final String PUBLISHERS = "publishers";
    private static final String TYPES = "types";
    private static final String AUTHORS = "authors";
    private static final String GENRES = "genres";
    private static final String SPACE = " ";
    private static final String POINT = ".";

    private static final String PAGE = "page";
    private static final String PAGES_COUNT = "pages_count";
    private static final String URL = "url";
    private static final String REDIRECT_PAGE = "&page=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Book list build started"));

        int page;
        String url = (String) request.getSession().getAttribute(URL);
        if (Util.isID(request.getParameter(PAGE))) {
            page = Integer.parseInt(request.getParameter(PAGE));
        } else {
            response.sendRedirect(url + REDIRECT_PAGE + 1);
            return;
        }

        BookCatalogFilter bookCatalogFilter = new BookCatalogFilter();
        Map<String, Object> filters = bookCatalogFilter.buildFilter(request);

        PublisherService publisherService = ServiceProvider.getInstance().getPublisherService();
        TypeService typeService = ServiceProvider.getInstance().getTypeService();
        AuthorService authorService = ServiceProvider.getInstance().getAuthorService();
        GenreService genreService = ServiceProvider.getInstance().getGenreService();

        List<Publisher> publishers;
        List<Type> types;
        List<Author> authors;
        List<Genre> genres;

        request.setAttribute(PAGE, page);
        request.setAttribute(URL, request.getQueryString().replaceAll(REDIRECT_PAGE + page, ""));

        BookCatalogService bookCatalogService = ServiceProvider.getInstance().getBookCatalogService();
        List<BookCatalog> bookCatalog;
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

            bookCatalog = bookCatalogService.getBookCatalogByFilter(filters, page);
            request.setAttribute(BOOK_CATALOG, bookCatalog);

            int pagesCount = bookCatalogService.getPagesCount(filters);
            request.setAttribute(PAGES_COUNT, pagesCount);

            logger.info(LogMessageBuilder.message(logMessage, "Book list building completed"));

            RequestProvider.forward(PagePath.BOOK_LIST_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error getting data for book list"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
