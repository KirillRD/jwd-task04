package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.entity.user.Reader;
import com.epam.library.entity.user.ReaderListFilterName;
import com.epam.library.service.ReaderService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReaderListPage implements Command {

    private static final String READER_LIST = "reader_list";
    private static final String CHECKED = "checked";
    private static final String SELECTED = "selected";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReaderService readerService = ServiceProvider.getInstance().getReaderService();

        Map<String, Object> filters = new LinkedHashMap<>();
        if (isValidInsertFilter(ReaderListFilterName.LAST_NAME, request)) {
            filters.put(ReaderListFilterName.LAST_NAME, request.getParameter(ReaderListFilterName.LAST_NAME));
            request.setAttribute(ReaderListFilterName.LAST_NAME, request.getParameter(ReaderListFilterName.LAST_NAME));
        }
        if (isValidInsertFilter(ReaderListFilterName.DEBTORS, request)) {
            filters.put(ReaderListFilterName.DEBTORS, request.getParameter(ReaderListFilterName.DEBTORS));
            request.setAttribute(ReaderListFilterName.DEBTORS, CHECKED);
        }
        if (isValidInsertFilter(ReaderListFilterName.RESERVATION_DATE_FROM, request)) {
            filters.put(ReaderListFilterName.RESERVATION_DATE_FROM, request.getParameter(ReaderListFilterName.RESERVATION_DATE_FROM));
            request.setAttribute(ReaderListFilterName.RESERVATION_DATE_FROM, request.getParameter(ReaderListFilterName.RESERVATION_DATE_FROM));
        }
        if (isValidInsertFilter(ReaderListFilterName.RESERVATION_DATE_TO, request)) {
            filters.put(ReaderListFilterName.RESERVATION_DATE_TO, request.getParameter(ReaderListFilterName.RESERVATION_DATE_TO));
            request.setAttribute(ReaderListFilterName.RESERVATION_DATE_TO, request.getParameter(ReaderListFilterName.RESERVATION_DATE_TO));
        }
        if (isValidInsertFilter(ReaderListFilterName.RESERVATION, request)) {
            filters.put(ReaderListFilterName.RESERVATION, request.getParameter(ReaderListFilterName.RESERVATION));
            request.setAttribute(ReaderListFilterName.RESERVATION, CHECKED);
        }
        if (isValidInsertFilter(ReaderListFilterName.SORT, request)) {
            filters.put(ReaderListFilterName.SORT, request.getParameter(ReaderListFilterName.SORT));
            request.setAttribute(request.getParameter(ReaderListFilterName.SORT), SELECTED);
        } else {
            filters.put(ReaderListFilterName.SORT, ReaderListFilterName.LAST_NAME_ASCENDING);
        }

        List<Reader> readers;
        try {
            readers = readerService.getReadersByFilter(filters);
            request.setAttribute(READER_LIST, readers.toArray());
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.forward(PagePath.READER_LIST_PAGE, request, response);
    }

    private boolean isValidInsertFilter(String nameFilter, HttpServletRequest request) {
        String value = request.getParameter(nameFilter);
        if (value == null || value.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
