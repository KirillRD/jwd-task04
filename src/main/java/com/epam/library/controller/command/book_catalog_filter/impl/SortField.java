package com.epam.library.controller.command.book_catalog_filter.impl;

import com.epam.library.controller.command.book_catalog_filter.BookCatalogFilter;
import com.epam.library.controller.command.book_catalog_filter.FilterField;
import com.epam.library.constant.BookCatalogFilterName;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface for adding a sort field value to a filter
 */
public class SortField implements FilterField {

    private static final String SELECTED = "selected";

    public SortField() {}

    @Override
    public void execute(String filterName, String filterValue, BookCatalogFilter bookCatalogFilter, HttpServletRequest request) {
        if (BookCatalogFilterName.bookCatalogFilterSortValues.contains(filterValue)) {
            bookCatalogFilter.setSortValue(filterValue);
            request.setAttribute(filterValue, SELECTED);
            bookCatalogFilter.getFilterNames().remove(filterName);
        }
    }
}
