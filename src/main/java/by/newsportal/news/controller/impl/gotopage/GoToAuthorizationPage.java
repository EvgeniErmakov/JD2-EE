package by.newsportal.news.controller.impl.gotopage;

import java.io.IOException;

import by.newsportal.news.controller.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

public class GoToAuthorizationPage implements Command {
    private static final GoToAuthorizationPage INSTANCE = new GoToAuthorizationPage();
    private static final String AUTHORIZATION_PAGE = "/WEB-INF/jsp/authorizationPage.jsp";
    private static final String SESSION_PATH = "path";
    private static final String SESSION_PATH_COMMAND = "AUTHORIZATION_PAGE";

    public static GoToAuthorizationPage getInstance() {
        return INSTANCE;
    }

    private GoToAuthorizationPage() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession(true).setAttribute(SESSION_PATH, SESSION_PATH_COMMAND);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(AUTHORIZATION_PAGE);
        requestDispatcher.forward(request, response);
    }
}
