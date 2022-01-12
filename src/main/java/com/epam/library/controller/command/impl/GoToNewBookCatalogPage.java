package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.entity.book.catalog.BookCatalog;
import com.epam.library.service.BookCatalogService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class GoToNewBookCatalogPage implements Command {

    private static final String BOOK_CATALOG = "book_catalog";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookCatalogService bookCatalogService = ServiceProvider.getInstance().getBookCatalogService();
        List<BookCatalog> bookCatalog;
        try {
            bookCatalog = bookCatalogService.getNewBookCatalogList();
            request.setAttribute(BOOK_CATALOG, bookCatalog.toArray());
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.forward(PagePath.NEW_POPULAR_BOOK_CATALOG_PAGE, request, response);
    }
}
