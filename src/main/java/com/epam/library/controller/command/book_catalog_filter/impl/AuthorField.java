package com.epam.library.controller.command.book_catalog_filter.impl;

import com.epam.library.controller.command.book_catalog_filter.BookCatalogFilter;
import com.epam.library.controller.command.book_catalog_filter.FilterField;
import com.epam.library.controller.util.Util;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface for adding an author field value to a filter
 */
public class AuthorField implements FilterField {

    public AuthorField() {}

    @Override
    public void execute(String filterName, String filterValue, BookCatalogFilter bookCatalogFilter, HttpServletRequest request) {
        if (Util.isID(filterValue)) {
            bookCatalogFilter.getAuthorsID().add(filterValue);
        }
    }
}
