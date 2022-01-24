package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
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

public class ReaderListPage implements Command {
    private static final Logger logger = Logger.getLogger(ReaderListPage.class.getName());

    private static final String READER_LIST = "reader_list";
    private static final String CHECKED = "checked";
    private static final String ON = "on";
    private static final String SELECTED = "selected";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogMessageBuilder logMesBuilder = new LogMessageBuilder(request);
        logger.info(logMesBuilder.build("Reader list build started"));

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

        List<Reader> readers;
        try {
            readers = readerService.getReadersByFilter(filters);
            request.setAttribute(READER_LIST, readers);
            logger.info(logMesBuilder.build("Reader list building completed"));

            RequestProvider.forward(PagePath.READER_LIST_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error getting data for reader list"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
