package by.newsportal.news.controller.impl;

import by.newsportal.news.controller.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToAddNewsPage implements Command {
    private static final GoToAddNewsPage INSTANCE = new GoToAddNewsPage();

    public static GoToAddNewsPage getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = "/WEB-INF/jsp/AddNewsPage.jsp";
        String lastCommandName = "GO_TO_ADD_NEWS_PAGE";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        request.getSession(true).setAttribute("path", lastCommandName);
        requestDispatcher.forward(request, response);
    }
}
