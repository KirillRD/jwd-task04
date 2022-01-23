package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.controller.command.util.Util;
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
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class InstancePage implements Command {
    private static final Logger logger = Logger.getLogger(InstancePage.class.getName());
    private LogMessageBuilder logMesBuilder;

    private static final String BOOK_ID = "book_id";
    private static final String BOOK_INFO = "book_info";
    private static final String BOOK_INSTANCES = "book_instances";
    private static final String INSTANCE = "instance";
    private static final String INSTANCE_ID = "instance_id";
    private static final String HALLS = "halls";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logMesBuilder = new LogMessageBuilder(request);

        BookCatalog bookInfo;
        List<BookInstance> bookInstances;
        List<Hall> halls;
        BookCatalogService bookCatalogService = ServiceProvider.getInstance().getBookCatalogService();
        InstanceService instanceService = ServiceProvider.getInstance().getInstanceService();
        HallService hallService = ServiceProvider.getInstance().getHallService();
        logger.info(logMesBuilder.build("Instance list build started"));

        int bookID;
        if (Util.isID(request.getParameter(BOOK_ID))) {
            bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        } else {
            logger.error(logMesBuilder.build("Invalid page attributes. Book was not found"));
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
            return;
        }

        try {
            halls = hallService.getHallList();
            request.setAttribute(HALLS, halls);

            bookInfo = bookCatalogService.getBookCatalog(bookID);
            if (bookInfo == null) {
                logger.error(logMesBuilder.build("Invalid page attributes. Book was not found"));
                RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            request.setAttribute(BOOK_INFO, bookInfo);

            bookInstances = instanceService.getBookInstances(bookID);
            request.setAttribute(BOOK_INSTANCES, bookInstances);

            if (Util.isID(request.getParameter(INSTANCE_ID))) {
                int instanceID = Integer.parseInt(request.getParameter(INSTANCE_ID));
                BookInstance instance = instanceService.getBookInstance(instanceID);
                if (instance == null) {
                    logger.error(logMesBuilder.build("Invalid page attributes. Instance was not found"));
                    RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                    return;
                }
                HttpSession session = request.getSession();
                if (session.getAttribute(INSTANCE) == null) {
                    session.setAttribute(INSTANCE, instance);
                }
            }
            logger.info(logMesBuilder.build("Instance list building completed"));

            RequestProvider.forward(PagePath.INSTANCE_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error getting data for instance list"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
