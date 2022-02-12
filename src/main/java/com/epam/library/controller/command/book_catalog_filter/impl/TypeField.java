package com.epam.library.controller.command.book_catalog_filter.impl;

import com.epam.library.controller.command.book_catalog_filter.BookCatalogFilter;
import com.epam.library.controller.command.book_catalog_filter.FilterField;
import com.epam.library.controller.util.Util;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface for adding a type field value to a filter
 */
public class TypeField implements FilterField {

    private static final String SAVED_TYPE = "saved_type";

    public TypeField() {}

    @Override
    public void execute(String filterName, String filterValue, BookCatalogFilter bookCatalogFilter, HttpServletRequest request) {
        if (Util.isID(filterValue)) {
            bookCatalogFilter.getFilters().put(filterName, filterValue);
            request.setAttribute(SAVED_TYPE, filterValue);
            bookCatalogFilter.getFilterNames().remove(filterName);
        }
    }
}
