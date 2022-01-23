package com.epam.library.controller.command.book_catalog.impl;

import com.epam.library.controller.command.book_catalog.BookCatalogFilter;
import com.epam.library.controller.command.book_catalog.FilterField;
import com.epam.library.controller.command.util.Util;
import jakarta.servlet.http.HttpServletRequest;

public class PageField implements FilterField {

    public PageField() {}

    @Override
    public void execute(String filterName, String filterValue, HttpServletRequest request) {
        if (Util.isID(filterValue)) {
            BookCatalogFilter.filters.put(filterName, filterValue);
            request.setAttribute(filterName, filterValue);
            BookCatalogFilter.filterNames.remove(filterName);
        }
    }
}
