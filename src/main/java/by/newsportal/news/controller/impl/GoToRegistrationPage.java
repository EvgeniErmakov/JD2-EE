package by.newsportal.news.controller.impl;

import java.io.IOException;

import by.newsportal.news.controller.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToRegistrationPage implements Command {
    private static final GoToRegistrationPage INSTANCE = new GoToRegistrationPage();
    private static final String SESSION_PATH = "path";
    private static final String PATH_COMMAND_REG = "REGISTRATION_PAGE";
    private static final String REGISTRATION_PAGE = "/WEB-INF/jsp/registrationPage.jsp";

    private GoToRegistrationPage() {
    }

    public static GoToRegistrationPage getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_REG);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(REGISTRATION_PAGE);
        requestDispatcher.forward(request, response);
    }
}
