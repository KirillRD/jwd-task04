package com.epam.library.controller;

import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.CommandProvider;
import com.epam.library.controller.command.constant.ErrorMessage;
import com.epam.library.controller.command.constant.PagePath;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class Controller extends HttpServlet {

    private static final String COMMAND = "command";

    private final CommandProvider commandProvider = new CommandProvider();

    public Controller() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);
        Command command = commandProvider.getCommand(commandName);

        if (commandName == null || command == null) {
            request.setAttribute(ErrorMessage.ERROR, ErrorMessage.PAGE_NOT_FOUND);
            RequestProvider.forward(PagePath.ERROR_PAGE, request, response);
            return;
        }

        command.execute(request, response);
    }
}
