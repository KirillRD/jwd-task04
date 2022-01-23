package com.epam.library.controller.command.book_catalog.impl;

import com.epam.library.controller.command.book_catalog.BookCatalogFilter;
import com.epam.library.controller.command.book_catalog.FilterField;
import com.epam.library.constant.BookCatalogFilterName;
import jakarta.servlet.http.HttpServletRequest;

public class SortField implements FilterField {

    private static final String SELECTED = "selected";

    public SortField() {}

    @Override
    public void execute(String filterName, String filterValue, HttpServletRequest request) {
        if (BookCatalogFilterName.bookCatalogFilterSortValues.contains(filterValue)) {
            BookCatalogFilter.sortValue = filterValue;
            request.setAttribute(filterValue, SELECTED);
            BookCatalogFilter.filterNames.remove(filterName);
        }
    }
}
