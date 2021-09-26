package by.newsportal.news.controller.impl;

import java.io.IOException;

import by.newsportal.news.controller.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UnknownCommand implements Command {
    private static final UnknownCommand INSTANCE = new UnknownCommand();
    private static final String SESSION_PATH = "path";
    private static final String PATH_COMMAND_ERR = "UNKNOWN_COMMAND";
    private static final String ERROR_PAGE = "/WEB-INF/jsp/error.jsp";

    private UnknownCommand() {
    }

    public static UnknownCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_ERR);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ERROR_PAGE);
        requestDispatcher.forward(request, response);
    }
}
