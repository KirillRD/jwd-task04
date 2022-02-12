package com.epam.library.controller.command.book_catalog_filter.impl;

import com.epam.library.controller.command.book_catalog_filter.BookCatalogFilter;
import com.epam.library.controller.command.book_catalog_filter.FilterField;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface for adding name or ISBN or ISSN field value to filter
 */
public class DefaultField implements FilterField {

    public DefaultField() {}

    @Override
    public void execute(String filterName, String filterValue, BookCatalogFilter bookCatalogFilter, HttpServletRequest request) {
        bookCatalogFilter.getFilters().put(filterName, filterValue);
        request.setAttribute(filterName, filterValue);
        bookCatalogFilter.getFilterNames().remove(filterName);
    }
}
