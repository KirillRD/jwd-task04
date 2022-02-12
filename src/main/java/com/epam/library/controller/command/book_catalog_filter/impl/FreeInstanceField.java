package com.epam.library.controller.command.book_catalog_filter.impl;

import com.epam.library.controller.command.book_catalog_filter.BookCatalogFilter;
import com.epam.library.controller.command.book_catalog_filter.FilterField;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface for adding a free instance field value to a filter
 */
public class FreeInstanceField implements FilterField {

    private static final String ON = "on";
    private static final String CHECKED = "checked";

    public FreeInstanceField() {}

    @Override
    public void execute(String filterName, String filterValue, BookCatalogFilter bookCatalogFilter, HttpServletRequest request) {
        if (filterValue.equals(ON)) {
            bookCatalogFilter.getFilters().put(filterName, filterValue);
            request.setAttribute(filterName, CHECKED);
            bookCatalogFilter.getFilterNames().remove(filterName);
        }
    }
}
