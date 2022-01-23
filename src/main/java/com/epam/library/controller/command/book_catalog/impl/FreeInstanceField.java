package com.epam.library.controller.command.book_catalog.impl;

import com.epam.library.controller.command.book_catalog.BookCatalogFilter;
import com.epam.library.controller.command.book_catalog.FilterField;
import jakarta.servlet.http.HttpServletRequest;

public class FreeInstanceField implements FilterField {

    private static final String ON = "on";
    private static final String CHECKED = "checked";

    public FreeInstanceField() {}

    @Override
    public void execute(String filterName, String filterValue, HttpServletRequest request) {
        if (filterValue.equals(ON)) {
            BookCatalogFilter.filters.put(filterName, filterValue);
            request.setAttribute(filterName, CHECKED);
            BookCatalogFilter.filterNames.remove(filterName);
        }
    }
}
