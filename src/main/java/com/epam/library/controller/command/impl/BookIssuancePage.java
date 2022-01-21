package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
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
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class BookIssuancePage implements Command {
    private static final Logger logger = Logger.getLogger(BookIssuancePage.class.getName());

    private static final String READER_ID = "reader_id";
    private static final String READER = "reader";

    private static final String BOOK_CATALOG = "book_catalog";
    private static final String CHECKED = "checked";
    private static final String ON = "on";
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
    private static final int PUBLICATION_YEAR_LENGTH = 4;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> filters = new LinkedHashMap<>();

        Set<String> filterNames = new HashSet<>(BookCatalogFilterName.bookCatalogFilterName);
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        String sortValue = null;
        List<String> authorsID = new ArrayList<>();
        List<String> genresID = new ArrayList<>();
        for (String filterName : requestParameterMap.keySet()) {
            if (filterNames.contains(filterName)) {
                for (String filterValue : requestParameterMap.get(filterName)) {
                    if (!filterValue.isEmpty()) {
                        switch (filterName) {
                            case BookCatalogFilterName.SORT:
                                if (BookCatalogFilterName.bookCatalogFilterSortValues.contains(filterValue)) {
                                    sortValue = filterValue;
                                    request.setAttribute(filterValue, SELECTED);
                                    filterNames.remove(filterName);
                                }
                                break;
                            case BookCatalogFilterName.FREE_INSTANCES:
                                if (filterValue.equals(ON)) {
                                    filters.put(filterName, filterValue);
                                    request.setAttribute(filterName, CHECKED);
                                    filterNames.remove(filterName);
                                }
                                break;
                            case BookCatalogFilterName.AUTHORS:
                                if (isInteger(filterValue)) {
                                    authorsID.add(filterValue);
                                }
                                break;
                            case BookCatalogFilterName.GENRES:
                                if (isInteger(filterValue)) {
                                    genresID.add(filterValue);
                                }
                                break;
                            case BookCatalogFilterName.PUBLISHER:
                                if (isInteger(filterValue)) {
                                    filters.put(filterName, filterValue);
                                    request.setAttribute(SAVED_PUBLISHER, filterValue);
                                    filterNames.remove(filterName);
                                }
                                break;
                            case BookCatalogFilterName.TYPE:
                                if (isInteger(filterValue)) {
                                    filters.put(filterName, filterValue);
                                    request.setAttribute(SAVED_TYPE, filterValue);
                                    filterNames.remove(filterName);
                                }
                                break;
                            case BookCatalogFilterName.PAGES_FROM:
                            case BookCatalogFilterName.PAGES_TO:
                                if (isInteger(filterValue)) {
                                    filters.put(filterName, filterValue);
                                    request.setAttribute(filterName, filterValue);
                                    filterNames.remove(filterName);
                                }
                                break;
                            case BookCatalogFilterName.PUBLICATION_YEAR_FROM:
                            case BookCatalogFilterName.PUBLICATION_YEAR_TO:
                                if (isInteger(filterValue) && filterValue.length() == PUBLICATION_YEAR_LENGTH) {
                                    filters.put(filterName, filterValue);
                                    request.setAttribute(filterName, filterValue);
                                    filterNames.remove(filterName);
                                }
                                break;
                            default:
                                filters.put(filterName, filterValue);
                                request.setAttribute(filterName, filterValue);
                                filterNames.remove(filterName);
                        }
                    }
                }
            }
        }
        if (!authorsID.isEmpty()) {
            request.setAttribute(SAVED_AUTHORS, authorsID);
            filters.put(BookCatalogFilterName.AUTHORS, authorsID);
        }

        if (!genresID.isEmpty()) {
            request.setAttribute(SAVED_GENRES, genresID);
            filters.put(BookCatalogFilterName.GENRES, genresID);
        }

        if (sortValue != null) {
            filters.put(BookCatalogFilterName.SORT, sortValue);
        } else {
            filters.put(BookCatalogFilterName.SORT, BookCatalogFilterName.NAME_ASCENDING);
        }

        PublisherService publisherService = ServiceProvider.getInstance().getPublisherService();
        TypeService typeService = ServiceProvider.getInstance().getTypeService();
        AuthorService authorService = ServiceProvider.getInstance().getAuthorService();
        GenreService genreService = ServiceProvider.getInstance().getGenreService();

        List<Publisher> publishers;
        List<Type> types;
        List<Author> authors;
        List<Genre> genres;

        BookCatalogService bookCatalogService = ServiceProvider.getInstance().getBookCatalogService();
        ReaderService readerService = ServiceProvider.getInstance().getReaderService();
        Reader reader;
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

            if (request.getParameter(READER_ID) == null) {
                RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            int readerID = Integer.parseInt(request.getParameter(READER_ID));
            reader = readerService.getReader(readerID);
            if (reader == null) {
                RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            request.setAttribute(READER, reader);

            bookCatalog = bookCatalogService.getBookCatalogByFilter(filters);
            request.setAttribute(BOOK_CATALOG, bookCatalog);

            RequestProvider.forward(PagePath.BOOK_ISSUANCE_PAGE, request, response);
        } catch (ServiceException e) {
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }

    public boolean isInteger(CharSequence cs) {
        if (cs == null || cs.length() == 0 || (cs.length() == 1 && cs.charAt(0) == '0')) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
