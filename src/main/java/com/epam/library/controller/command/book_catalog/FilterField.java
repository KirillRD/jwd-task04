package com.epam.library.controller.command.book_catalog;

import jakarta.servlet.http.HttpServletRequest;

public interface FilterField {
    void execute(String filterName, String filterValue, HttpServletRequest request);
}
