package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.PagePath;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.entity.user.Reader;
import com.epam.library.constant.ReaderListFilterName;
import com.epam.library.service.ReaderService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

/**
 * Command to go to reader list page and filter readers
 */
public class ReaderListPage implements Command {
    private static final Logger logger = Logger.getLogger(ReaderListPage.class.getName());

    private static final String READER_LIST = "reader_list";
    private static final String CHECKED = "checked";
    private static final String ON = "on";
    private static final String SELECTED = "selected";

    private static final String PAGE = "page";
    private static final String PAGES_COUNT = "pages_count";
    private static final String URL = "url";
    private static final String REDIRECT_PAGE = "&page=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Reader list build started"));

        int page;
        String url = (String) request.getSession().getAttribute(URL);
        if (Util.isID(request.getParameter(PAGE))) {
            page = Integer.parseInt(request.getParameter(PAGE));
        } else {
            response.sendRedirect(url + REDIRECT_PAGE + 1);
            return;
        }

        ReaderService readerService = ServiceProvider.getInstance().getReaderService();

        Map<String, Object> filters = new LinkedHashMap<>();

        Set<String> filterNames = new HashSet<>(ReaderListFilterName.readerListFilterName);
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        String sortValue = null;
        for (String filterName : requestParameterMap.keySet()) {
            if (filterNames.contains(filterName)) {
                for (String filterValue : requestParameterMap.get(filterName)) {
                    if (!filterValue.isEmpty()) {
                        if (filterName.equals(ReaderListFilterName.SORT)) {
                            if (ReaderListFilterName.readerListFilterSortValues.contains(filterValue)) {
                                sortValue = filterValue;
                                request.setAttribute(filterValue, SELECTED);
                                filterNames.remove(filterName);
                            }
                        } else if (filterName.equals(ReaderListFilterName.DEBTORS) || filterName.equals(ReaderListFilterName.RESERVATION) ||
                                   filterName.equals(ReaderListFilterName.READING_HALL) || filterName.equals(ReaderListFilterName.RENTAL)) {
                            if (filterValue.equals(ON)) {
                                filters.put(filterName, filterValue);
                                request.setAttribute(filterName, CHECKED);
                                filterNames.remove(filterName);
                            }
                        } else {
                            filters.put(filterName, filterValue);
                            request.setAttribute(filterName, filterValue);
                            filterNames.remove(filterName);
                        }
                    }
                }
            }
        }

        if (sortValue != null) {
            filters.put(ReaderListFilterName.SORT, sortValue);
        } else {
            filters.put(ReaderListFilterName.SORT, ReaderListFilterName.LAST_NAME_ASCENDING);
        }

        request.setAttribute(PAGE, page);
        request.setAttribute(URL, request.getQueryString().replaceAll(REDIRECT_PAGE + page, ""));

        List<Reader> readers;
        try {
            readers = readerService.getReadersByFilter(filters, page);
            request.setAttribute(READER_LIST, readers);

            int pagesCount = readerService.getPagesCount(filters);
            request.setAttribute(PAGES_COUNT, pagesCount);

            logger.info(LogMessageBuilder.message(logMessage, "Reader list building completed"));

            RequestManager.forward(PagePath.READER_LIST_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error getting data for reader list"), e);
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
