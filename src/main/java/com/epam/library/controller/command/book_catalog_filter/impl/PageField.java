package com.epam.library.controller.command.book_catalog_filter.impl;

import com.epam.library.controller.command.book_catalog_filter.BookCatalogFilter;
import com.epam.library.controller.command.book_catalog_filter.FilterField;
import com.epam.library.controller.util.Util;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface for adding a page field value to a filter
 */
public class PageField implements FilterField {

    public PageField() {}

    @Override
    public void execute(String filterName, String filterValue, BookCatalogFilter bookCatalogFilter, HttpServletRequest request) {
        if (Util.isID(filterValue)) {
            bookCatalogFilter.getFilters().put(filterName, filterValue);
            request.setAttribute(filterName, filterValue);
            bookCatalogFilter.getFilterNames().remove(filterName);
        }
    }
}
