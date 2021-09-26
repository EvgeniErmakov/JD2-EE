package by.newsportal.news.controller.impl;

import by.newsportal.news.controller.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class GoToAddNewsPage implements Command {
    private static final GoToAddNewsPage INSTANCE = new GoToAddNewsPage();
    private static final String ADD_NEWS_PAGE = "/WEB-INF/jsp/addNewsPage.jsp";
    private static final String GO_TO_ADD_NEWS_PAGE = "GO_TO_ADD_NEWS_PAGE";
    private static final String SESSION_PATH = "path";

    public static GoToAddNewsPage getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ADD_NEWS_PAGE);
        request.getSession(true).setAttribute(SESSION_PATH, GO_TO_ADD_NEWS_PAGE);
        requestDispatcher.forward(request, response);
    }
}

