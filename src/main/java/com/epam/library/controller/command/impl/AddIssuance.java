package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.entity.Issuance;
import com.epam.library.service.*;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AddIssuance implements Command {

    private static final String READER_ID = "reader_id";
    private static final String INSTANCES = "instances";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IssuanceService issuanceService = ServiceProvider.getInstance().getIssuanceService();
        int readerID = Integer.parseInt(request.getParameter(READER_ID));
        try {
            for (String id : request.getParameterValues(INSTANCES)) {
                if (!id.isEmpty()) {
                    Issuance issuance = new Issuance();
                    issuance.setReaderID(readerID);
                    issuance.setInstanceID(Integer.parseInt(id));
                    issuanceService.addIssuance(issuance);
                }
            }
        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.redirect(String.format(RedirectCommand.BOOK_ISSUANCE_PAGE, readerID), request, response);
    }
}
