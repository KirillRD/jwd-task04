package com.epam.library.controller.command.book_catalog_filter.impl;

import com.epam.library.controller.command.book_catalog_filter.BookCatalogFilter;
import com.epam.library.controller.command.book_catalog_filter.FilterField;
import com.epam.library.controller.util.Util;
import jakarta.servlet.http.HttpServletRequest;

public class PublicationYearField implements FilterField {

    private static final int PUBLICATION_YEAR_LENGTH = 4;

    public PublicationYearField() {}

    @Override
    public void execute(String filterName, String filterValue, HttpServletRequest request) {
        if (Util.isID(filterValue) && filterValue.length() == PUBLICATION_YEAR_LENGTH) {
            BookCatalogFilter.filters.put(filterName, filterValue);
            request.setAttribute(filterName, filterValue);
            BookCatalogFilter.filterNames.remove(filterName);
        }
    }
}
