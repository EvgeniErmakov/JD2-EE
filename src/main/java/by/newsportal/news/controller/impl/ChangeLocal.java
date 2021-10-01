package by.newsportal.news.controller.impl;

import java.io.IOException;

import by.newsportal.news.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChangeLocal implements Command {
    private static final ChangeLocal INSTANCE = new ChangeLocal();
    private static final String SESSION_PATH = "path";
    private static final String SESSION_LOCAL = "local";
    private static final String PART_PATH = "Controller?command=";

    private ChangeLocal() {
    }

    public static ChangeLocal getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession(true).setAttribute(SESSION_LOCAL, request.getParameter(SESSION_LOCAL));
        response.sendRedirect(PART_PATH + request.getSession().getAttribute(SESSION_PATH));
    }
}
