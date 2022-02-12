package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.service.IssuanceService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Command to change status of book issuance
 */
public class ReaderIssuanceOperation implements Command {
    private static final Logger logger = Logger.getLogger(ReaderIssuanceOperation.class.getName());

    private static final String READER_ID = "reader_id";

    private static final String ISSUANCE_OPERATION = "issuance_operation";
    private static final String CHECK_ISSUANCE_OPERATION = "check_issuance_operation";

    private static final String ISSUANCE_MESSAGE = "issuance_message";
    private static final String ERROR_ISSUANCE_RESERVATION = "error-issuance-reservation";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Book issuance operation started"));

        IssuanceService issuanceService = ServiceProvider.getInstance().getIssuanceService();

        int readerID;
        if (Util.isID(request.getParameter(READER_ID))) {
            readerID = Integer.parseInt(request.getParameter(READER_ID));
        } else {
            logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Book issuance operation was failed"));
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
            return;
        }
        try {
            if (request.getParameter(ISSUANCE_OPERATION) != null && request.getParameterValues(CHECK_ISSUANCE_OPERATION) != null) {
                List<String> issues = Arrays.asList(request.getParameterValues(CHECK_ISSUANCE_OPERATION));
                String operation = request.getParameter(ISSUANCE_OPERATION);
                if (!issuanceService.updateConditionIssuance(issues, operation)) {
                    HttpSession session = request.getSession();
                    session.setAttribute(ISSUANCE_MESSAGE, ERROR_ISSUANCE_RESERVATION);
                    logger.info(LogMessageBuilder.message(logMessage, "Book issuance operation was failed. Incorrect data format"));
                } else {
                    logger.info(LogMessageBuilder.message(logMessage, "Book issuance operation completed"));
                }
            } else {
                logger.info(LogMessageBuilder.message(logMessage, "Book issuance operation was failed. Incorrect data format"));
            }

            RequestManager.redirect(String.format(RedirectCommand.READER_PAGE, readerID), request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error of the book issuance operation data"), e);
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
