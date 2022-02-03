package com.epam.library.controller.command.book_catalog_filter.impl;

import com.epam.library.controller.command.book_catalog_filter.BookCatalogFilter;
import com.epam.library.controller.command.book_catalog_filter.FilterField;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultField implements FilterField {

    public DefaultField() {}

    @Override
    public void execute(String filterName, String filterValue, HttpServletRequest request) {
        BookCatalogFilter.filters.put(filterName, filterValue);
        request.setAttribute(filterName, filterValue);
        BookCatalogFilter.filterNames.remove(filterName);
    }
}
