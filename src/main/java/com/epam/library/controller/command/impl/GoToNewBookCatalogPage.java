package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.LogMessageBuilder;
import com.epam.library.entity.book.catalog.BookCatalog;
import com.epam.library.service.BookCatalogService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class GoToNewBookCatalogPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToNewBookCatalogPage.class.getName());

    private static final String BOOK_CATALOG = "book_catalog";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogMessageBuilder logMesBuilder = new LogMessageBuilder(request);
        logger.info(logMesBuilder.build("Going to catalog page of new books started"));

        BookCatalogService bookCatalogService = ServiceProvider.getInstance().getBookCatalogService();
        List<BookCatalog> bookCatalog;
        try {
            bookCatalog = bookCatalogService.getNewBookCatalogList();
            request.setAttribute(BOOK_CATALOG, bookCatalog);
            logger.info(logMesBuilder.build("Going to catalog page of new books was completed"));

            RequestProvider.forward(PagePath.NEW_POPULAR_BOOK_CATALOG_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(logMesBuilder.build("Error in data while going to catalog page of new books"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
