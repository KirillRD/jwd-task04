package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.service.IssuanceService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ReaderIssuanceOperation implements Command {

    private static final String READER_ID = "reader_id";

    private static final String ISSUANCE_OPERATION = "issuance_operation";
    private static final String CHECK_ISSUANCE_OPERATION = "check_issuance_operation";
    private static final String RETURN = "return";
    private static final String EXTEND = "extend";
    private static final String LOST = "lost";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IssuanceService issuanceService = ServiceProvider.getInstance().getIssuanceService();

        try {

            if (request.getParameter(ISSUANCE_OPERATION) != null && request.getParameterValues(CHECK_ISSUANCE_OPERATION) != null) {
                switch (request.getParameter(ISSUANCE_OPERATION)) {
                    case RETURN:
                        for (String id : request.getParameterValues(CHECK_ISSUANCE_OPERATION)) {
                            issuanceService.updateReturnIssuance(Integer.parseInt(id));
                        }
                        break;
                    case EXTEND:
                        for (String id : request.getParameterValues(CHECK_ISSUANCE_OPERATION)) {
                            issuanceService.updateExtendIssuance(Integer.parseInt(id));
                        }
                        break;
                    case LOST:
                        for (String id : request.getParameterValues(CHECK_ISSUANCE_OPERATION)) {
                            issuanceService.updateLostIssuance(Integer.parseInt(id));
                        }
                        break;
                    default:
                }
            }
            int readerID = Integer.parseInt(request.getParameter(READER_ID));

            RequestProvider.redirect(String.format(RedirectCommand.READER_PAGE, readerID), request, response);
        } catch (ServiceException e) {
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
