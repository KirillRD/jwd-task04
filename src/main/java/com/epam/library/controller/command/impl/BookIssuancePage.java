package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.entity.book.Author;
import com.epam.library.entity.book.Genre;
import com.epam.library.entity.book.Publisher;
import com.epam.library.entity.book.Type;
import com.epam.library.entity.book.catalog.BookCatalog;
import com.epam.library.entity.book.catalog.BookCatalogFilterName;
import com.epam.library.entity.user.Reader;
import com.epam.library.service.*;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookIssuancePage implements Command {

    private static final String READER_ID = "reader_id";
    private static final String READER = "reader";

    private static final String BOOK_CATALOG = "book_catalog";
    private static final String CHECKED = "checked";
    private static final String SELECTED = "selected";
    private static final String PUBLISHERS = "publishers";
    private static final String TYPES = "types";
    private static final String AUTHORS = "authors";
    private static final String GENRES = "genres";
    private static final String SPACE = " ";
    private static final String POINT = ".";

    private static final String SAVED_PUBLISHER = "saved_publisher";
    private static final String SAVED_TYPE = "saved_type";
    private static final String SAVED_AUTHORS = "saved_authors";
    private static final String SAVED_GENRES = "saved_genres";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PublisherService publisherService = ServiceProvider.getInstance().getPublisherService();
        TypeService typeService = ServiceProvider.getInstance().getTypeService();
        AuthorService authorService = ServiceProvider.getInstance().getAuthorService();
        GenreService genreService = ServiceProvider.getInstance().getGenreService();

        List<Publisher> publishers = null;
        List<Type> types = null;
        List<Author> authors = null;
        List<Genre> genres = null;

        try {
            publishers = publisherService.getPublishersList();
            types = typeService.getTypesList();
            authors = authorService.getAuthorsList();
            genres = genreService.getGenresList();
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        for (Author author : authors) {
            author.setFirstName(SPACE + author.getFirstName().charAt(0) + POINT);
            if (!"".equals(author.getFatherName())) {
                author.setFatherName(author.getFatherName().charAt(0) + POINT);
            }
        }

        request.setAttribute(PUBLISHERS, publishers.toArray());
        request.setAttribute(TYPES, types.toArray());
        request.setAttribute(AUTHORS, authors.toArray());
        request.setAttribute(GENRES, genres.toArray());


        Map<String, Object> filters = new LinkedHashMap<>();
        if (isValidInsertFilter(BookCatalogFilterName.NAME, request, false)) {
            filters.put(BookCatalogFilterName.NAME, request.getParameter(BookCatalogFilterName.NAME).trim());
            request.setAttribute(BookCatalogFilterName.NAME, request.getParameter(BookCatalogFilterName.NAME));
        }
        if (isValidInsertFilter(BookCatalogFilterName.AUTHORS, request, true)) {
            filters.put(BookCatalogFilterName.AUTHORS, request.getParameterValues(BookCatalogFilterName.AUTHORS));
            request.setAttribute(SAVED_AUTHORS, request.getParameterValues(BookCatalogFilterName.AUTHORS));
        }
        if (isValidInsertFilter(BookCatalogFilterName.GENRES, request, true)) {
            filters.put(BookCatalogFilterName.GENRES, request.getParameterValues(BookCatalogFilterName.GENRES));
            request.setAttribute(SAVED_GENRES, request.getParameterValues(BookCatalogFilterName.GENRES));
        }
        if (isValidInsertFilter(BookCatalogFilterName.PUBLISHER, request, false)) {
            filters.put(BookCatalogFilterName.PUBLISHER, request.getParameter(BookCatalogFilterName.PUBLISHER));
            request.setAttribute(SAVED_PUBLISHER, request.getParameter(BookCatalogFilterName.PUBLISHER));
        }
        if (isValidInsertFilter(BookCatalogFilterName.PUBLICATION_YEAR_FROM, request, false)) {
            filters.put(BookCatalogFilterName.PUBLICATION_YEAR_FROM, request.getParameter(BookCatalogFilterName.PUBLICATION_YEAR_FROM).trim());
            request.setAttribute(BookCatalogFilterName.PUBLICATION_YEAR_FROM, request.getParameter(BookCatalogFilterName.PUBLICATION_YEAR_FROM));
        }
        if (isValidInsertFilter(BookCatalogFilterName.PUBLICATION_YEAR_TO, request, false)) {
            filters.put(BookCatalogFilterName.PUBLICATION_YEAR_TO, request.getParameter(BookCatalogFilterName.PUBLICATION_YEAR_TO).trim());
            request.setAttribute(BookCatalogFilterName.PUBLICATION_YEAR_TO, request.getParameter(BookCatalogFilterName.PUBLICATION_YEAR_TO));
        }
        if (isValidInsertFilter(BookCatalogFilterName.PAGES_TO, request, false)) {
            filters.put(BookCatalogFilterName.PAGES_TO, request.getParameter(BookCatalogFilterName.PAGES_TO).trim());
            request.setAttribute(BookCatalogFilterName.PAGES_TO, request.getParameter(BookCatalogFilterName.PAGES_TO));
        }
        if (isValidInsertFilter(BookCatalogFilterName.PAGES_FROM, request, false)) {
            filters.put(BookCatalogFilterName.PAGES_FROM, request.getParameter(BookCatalogFilterName.PAGES_FROM).trim());
            request.setAttribute(BookCatalogFilterName.PAGES_FROM, request.getParameter(BookCatalogFilterName.PAGES_FROM));
        }
        if (isValidInsertFilter(BookCatalogFilterName.ISBN, request, false)) {
            filters.put(BookCatalogFilterName.ISBN, request.getParameter(BookCatalogFilterName.ISBN).trim());
            request.setAttribute(BookCatalogFilterName.ISBN, request.getParameter(BookCatalogFilterName.ISBN));
        }
        if (isValidInsertFilter(BookCatalogFilterName.ISSN, request, false)) {
            filters.put(BookCatalogFilterName.ISSN, request.getParameter(BookCatalogFilterName.ISSN).trim());
            request.setAttribute(BookCatalogFilterName.ISSN, request.getParameter(BookCatalogFilterName.ISSN));
        }
        if (isValidInsertFilter(BookCatalogFilterName.TYPE, request, false)) {
            filters.put(BookCatalogFilterName.TYPE, request.getParameter(BookCatalogFilterName.TYPE));
            request.setAttribute(SAVED_TYPE, request.getParameter(BookCatalogFilterName.TYPE));
        }
        if (isValidInsertFilter(BookCatalogFilterName.FREE_INSTANCES, request, false)) {
            filters.put(BookCatalogFilterName.FREE_INSTANCES, request.getParameter(BookCatalogFilterName.FREE_INSTANCES));
            request.setAttribute(BookCatalogFilterName.FREE_INSTANCES, CHECKED);
        }
        if (isValidInsertFilter(BookCatalogFilterName.SORT, request, false)) {
            filters.put(BookCatalogFilterName.SORT, request.getParameter(BookCatalogFilterName.SORT));
            request.setAttribute(request.getParameter(BookCatalogFilterName.SORT), SELECTED);
        } else {
            filters.put(BookCatalogFilterName.SORT, BookCatalogFilterName.NAME_ASCENDING);
        }


        BookCatalogService bookCatalogService = ServiceProvider.getInstance().getBookCatalogService();
        ReaderService readerService = ServiceProvider.getInstance().getReaderService();
        Reader reader;
        List<BookCatalog> bookCatalog;
        try {
            int readerID = Integer.parseInt(request.getParameter(READER_ID));
            reader = readerService.getReader(readerID);
            request.setAttribute(READER, reader);

            bookCatalog = bookCatalogService.getBookCatalogByFilter(filters);
            request.setAttribute(BOOK_CATALOG, bookCatalog.toArray());
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.forward(PagePath.BOOK_ISSUANCE_PAGE, request, response);
    }

    private boolean isValidInsertFilter(String nameFilter, HttpServletRequest request, boolean array) {
        if (array) {
            String[] values = request.getParameterValues(nameFilter);
            if (values == null || values.length == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            String value = request.getParameter(nameFilter);
            if (value == null || value.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
    }
}
