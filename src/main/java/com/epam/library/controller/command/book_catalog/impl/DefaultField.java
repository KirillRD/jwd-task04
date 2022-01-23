package com.epam.library.controller.command.book_catalog.impl;

import com.epam.library.controller.command.book_catalog.BookCatalogFilter;
import com.epam.library.controller.command.book_catalog.FilterField;
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
