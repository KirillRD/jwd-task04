package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestManager;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.PagePath;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.entity.issuance.ReaderIssuance;
import com.epam.library.entity.reservation.ReaderReservation;
import com.epam.library.entity.user.Reader;
import com.epam.library.service.ReaderService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Command to go to reader's page
 */
public class GoToReaderPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToReaderPage.class.getName());

    private static final String READER_ID = "reader_id";
    private static final String READER = "reader";
    private static final String READER_ISSUANCE = "reader_issuance";
    private static final String READER_ISSUANCE_HISTORY = "reader_issuance_history";
    private static final String READER_RESERVATION = "reader_reservation";
    private static final String READER_RESERVATION_HISTORY = "reader_reservation_history";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Going to reader page started"));

        Reader reader;
        List<ReaderIssuance> readerIssuanceList;
        List<ReaderIssuance> readerIssuanceHistoryList;
        List<ReaderReservation> readerReservationList;
        List<ReaderReservation> readerReservationHistoryList;
        ReaderService readerService = ServiceProvider.getInstance().getReaderService();

        try {
            int readerID;
            if (Util.isID(request.getParameter(READER_ID))) {
                readerID = Integer.parseInt(request.getParameter(READER_ID));
            } else {
                logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Going to reader page is failed"));
                RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            reader = readerService.getReader(readerID);
            if (reader == null) {
                logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Going to reader page is failed"));
                RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
                return;
            }
            request.setAttribute(READER, reader);

            readerIssuanceList = readerService.getReaderIssuanceList(readerID);
            request.setAttribute(READER_ISSUANCE, readerIssuanceList);

            readerIssuanceHistoryList = readerService.getReaderIssuanceHistoryList(readerID);
            request.setAttribute(READER_ISSUANCE_HISTORY, readerIssuanceHistoryList);

            readerReservationList = readerService.getReaderReservationList(readerID);
            request.setAttribute(READER_RESERVATION, readerReservationList);

            readerReservationHistoryList = readerService.getReaderReservationHistoryList(readerID);
            request.setAttribute(READER_RESERVATION_HISTORY, readerReservationHistoryList);
            logger.info(LogMessageBuilder.message(logMessage, "Going to reader page was completed"));

            RequestManager.forward(PagePath.READER_PAGE, request, response);
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error in data while going to reader page"), e);
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
