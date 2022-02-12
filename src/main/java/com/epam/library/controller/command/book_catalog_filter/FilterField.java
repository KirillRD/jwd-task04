package com.epam.library.controller.command.book_catalog_filter;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface for adding a field value to a filter
 */
public interface FilterField {
    void execute(String filterName, String filterValue, BookCatalogFilter bookCatalogFilter, HttpServletRequest request);
}
