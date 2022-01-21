package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.controller.command.util.Util;
import com.epam.library.entity.Issuance;
import com.epam.library.service.*;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddIssuance implements Command {
    private static final Logger logger = Logger.getLogger(AddIssuance.class.getName());

    private static final String READER_ID = "reader_id";
    private static final String INSTANCES = "instances";

    private static final String MESSAGE = "message";
    private static final String MESSAGE_NOT_ISSUED_BOOKS = "message_not_issued_books";
    private static final String ERROR_ADD_ISSUANCE = "error-add-issuance";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IssuanceService issuanceService = ServiceProvider.getInstance().getIssuanceService();
        logger.info(logMessageBuilder("Books issuing started", request));

        int readerID;
        if (Util.isID(request.getParameter(READER_ID))) {
            readerID = Integer.parseInt(request.getParameter(READER_ID));
        } else {
            logger.error(logMessageBuilder("Invalid page attributes. Books were not issued", request));
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
            return;
        }
        try {
            List<Issuance> issues = new ArrayList<>();
            for (String id : request.getParameterValues(INSTANCES)) {
                if (!id.isEmpty()) {
                    Issuance issuance = new Issuance();
                    issuance.setReaderID(readerID);
                    issuance.setInstanceID(Integer.parseInt(id));
                    issues.add(issuance);
                }
            }
            if (!issues.isEmpty()) {
                String message = issuanceService.addIssuance(issues);
                if (!message.isEmpty()) {
                    HttpSession session = request.getSession();
                    session.setAttribute(MESSAGE, ERROR_ADD_ISSUANCE);
                    session.setAttribute(MESSAGE_NOT_ISSUED_BOOKS, message);
                    logger.info(logMessageBuilder("Not all books issue completed. Selected instances are already taken", request));
                } else {
                    logger.info(logMessageBuilder("Books issue completed", request));
                }
            }

            RequestProvider.redirect(String.format(RedirectCommand.BOOK_ISSUANCE_PAGE, readerID), request, response);
        } catch (ServiceException e) {
            logger.error(logMessageBuilder("Data error issuing books", request), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
