package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.entity.issuance.ReaderIssuance;
import com.epam.library.entity.reservation.ReaderReservation;
import com.epam.library.entity.user.Reader;
import com.epam.library.service.ReaderService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class GoToReaderPage implements Command {

    private static final String READER_ID = "reader_id";
    private static final String READER = "reader";
    private static final String READER_ISSUANCE = "reader_issuance";
    private static final String READER_ISSUANCE_HISTORY = "reader_issuance_history";
    private static final String READER_RESERVATION = "reader_reservation";
    private static final String READER_RESERVATION_HISTORY = "reader_reservation_history";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Reader reader;
        List<ReaderIssuance> readerIssuanceList;
        List<ReaderIssuance> readerIssuanceHistoryList;
        List<ReaderReservation> readerReservationList;
        List<ReaderReservation> readerReservationHistoryList;
        ReaderService readerService = ServiceProvider.getInstance().getReaderService();

        try {
            int readerID = Integer.parseInt(request.getParameter(READER_ID));
            reader = readerService.getReader(readerID);
            request.setAttribute(READER, reader);

            readerIssuanceList = readerService.getReaderIssuanceList(readerID);
            request.setAttribute(READER_ISSUANCE, readerIssuanceList);

            readerIssuanceHistoryList = readerService.getReaderIssuanceHistoryList(readerID);
            request.setAttribute(READER_ISSUANCE_HISTORY, readerIssuanceHistoryList);

            readerReservationList = readerService.getReaderReservationList(readerID);
            request.setAttribute(READER_RESERVATION, readerReservationList);

            readerReservationHistoryList = readerService.getReaderReservationHistoryList(readerID);
            request.setAttribute(READER_RESERVATION_HISTORY, readerReservationHistoryList);
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.forward(PagePath.READER_PAGE, request, response);
    }
}
