package com.epam.library.controller.command.impl;

import com.epam.library.controller.RequestProvider;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.constant.PagePath;
import com.epam.library.controller.command.constant.RedirectCommand;
import com.epam.library.entity.Instance;
import com.epam.library.service.InstanceService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

public class AddEditInstance implements Command {

    private static final String BOOK_ID = "book_id";
    private static final String INSTANCE_ID = "instance_id";

    private static final String NUMBER = "number";
    private static final String HALL = "hall";
    private static final String RECEIVED_DATE = "received_date";
    private static final String WRITE_OFF_DATE = "write_off_date";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InstanceService instanceService = ServiceProvider.getInstance().getInstanceService();
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        try {

            Instance instance = new Instance();
            instance.setBookID(bookID);
            instance.setNumber(request.getParameter(NUMBER));
            instance.setHallID(Integer.parseInt(request.getParameter(HALL)));
            Date date = Date.valueOf(request.getParameter(RECEIVED_DATE));
            instance.setReceivedDate(date);
            if (!"".equals(request.getParameter(WRITE_OFF_DATE))) {
                date = Date.valueOf(request.getParameter(WRITE_OFF_DATE));
                instance.setWriteOffDate(date);
            }
            if (request.getParameter(INSTANCE_ID) != null) {
                int instanceID = Integer.parseInt(request.getParameter(INSTANCE_ID));
                instance.setId(instanceID);
                instanceService.updateInstance(instance);
            } else {
                instanceService.addInstance(instance);
            }

        } catch (ServiceException e) {
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
        }

        RequestProvider.redirect(String.format(RedirectCommand.INSTANCE_PAGE, bookID), request, response);
    }
}
