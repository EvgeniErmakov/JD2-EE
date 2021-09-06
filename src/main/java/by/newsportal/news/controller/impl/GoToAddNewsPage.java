package by.newsportal.news.controller.impl;

import by.newsportal.news.controller.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class GoToAddNewsPage implements Command {
    private static final GoToAddNewsPage INSTANCE = new GoToAddNewsPage();
    public static final String ADD_NEWS_PAGE = "/WEB-INF/jsp/AddNewsPage.jsp";
    public static final String GO_TO_ADD_NEWS_PAGE = "GO_TO_ADD_NEWS_PAGE";

    public static GoToAddNewsPage getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ADD_NEWS_PAGE);
        request.getSession(true).setAttribute("path", GO_TO_ADD_NEWS_PAGE);
        requestDispatcher.forward(request, response);
    }
}

