package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.entity.book.catalog.BookCatalog;
import com.epam.library.entity.instance.BookInstance;
import com.epam.library.entity.instance.Hall;
import com.epam.library.service.BookCatalogService;
import com.epam.library.service.HallService;
import com.epam.library.service.InstanceService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class InstancePage implements Command {

    private static final String BOOK_ID = "book_id";
    private static final String BOOK_INFO = "book_info";
    private static final String BOOK_INSTANCES = "book_instances";
    private static final String INSTANCE = "instance";
    private static final String INSTANCE_ID = "instance_id";
    private static final String HALLS = "halls";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookCatalog bookInfo;
        List<BookInstance> bookInstances;
        List<Hall> halls;
        BookCatalogService bookCatalogService = ServiceProvider.getInstance().getBookCatalogService();
        InstanceService instanceService = ServiceProvider.getInstance().getInstanceService();
        HallService hallService = ServiceProvider.getInstance().getHallService();

        try {
            halls = hallService.getHallList();
            request.setAttribute(HALLS, halls);

            int bookID = Integer.parseInt(request.getParameter(BOOK_ID));

            if (request.getParameter(INSTANCE_ID) != null) {
                int instanceID = Integer.parseInt(request.getParameter(INSTANCE_ID));
                BookInstance instance = instanceService.getBookInstance(instanceID);
                request.setAttribute(INSTANCE, instance);
            }

            bookInfo = bookCatalogService.getBookCatalog(bookID);
            request.setAttribute(BOOK_INFO, bookInfo);

            bookInstances = instanceService.getBookInstances(bookInfo.getId());
            request.setAttribute(BOOK_INSTANCES, bookInstances);
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.forward(PagePath.INSTANCE_PAGE, request, response);
    }
}
