package com.epam.library.controller.command.book_catalog_filter.impl;

import com.epam.library.controller.command.book_catalog_filter.BookCatalogFilter;
import com.epam.library.controller.command.book_catalog_filter.FilterField;
import com.epam.library.controller.util.Util;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface for adding a publication year field value to a filter
 */
public class PublicationYearField implements FilterField {

    private static final int PUBLICATION_YEAR_LENGTH = 4;

    public PublicationYearField() {}

    @Override
    public void execute(String filterName, String filterValue, BookCatalogFilter bookCatalogFilter, HttpServletRequest request) {
        if (Util.isID(filterValue) && filterValue.length() == PUBLICATION_YEAR_LENGTH) {
            bookCatalogFilter.getFilters().put(filterName, filterValue);
            request.setAttribute(filterName, filterValue);
            bookCatalogFilter.getFilterNames().remove(filterName);
        }
    }
}
