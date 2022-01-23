package com.epam.library.controller.command.book_catalog.impl;

import com.epam.library.controller.command.book_catalog.BookCatalogFilter;
import com.epam.library.controller.command.book_catalog.FilterField;
import com.epam.library.controller.command.util.Util;
import jakarta.servlet.http.HttpServletRequest;

public class PublisherField implements FilterField {

    private static final String SAVED_PUBLISHER = "saved_publisher";

    public PublisherField() {}

    @Override
    public void execute(String filterName, String filterValue, HttpServletRequest request) {
        if (Util.isID(filterValue)) {
            BookCatalogFilter.filters.put(filterName, filterValue);
            request.setAttribute(SAVED_PUBLISHER, filterValue);
            BookCatalogFilter.filterNames.remove(filterName);
        }
    }
}
