package by.newsportal.news.controller.impl;

import by.newsportal.news.bean.News;
import by.newsportal.news.controller.Command;
import by.newsportal.news.service.NewsService;
import by.newsportal.news.service.ServiceProvider;
import by.newsportal.news.service.exception.ServiceException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class GoToUpdateNewsPage implements Command {
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final GoToUpdateNewsPage INSTANCE = new GoToUpdateNewsPage();

    public GoToUpdateNewsPage() {
    }

    public static GoToUpdateNewsPage getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = "/WEB-INF/jsp/updateNewsPage.jsp";
        HttpSession session = request.getSession(true);
        int choosenNewsId = Integer.parseInt(request.getParameter("choosenNewsId"));
        System.out.println("я отработал");
        String lastCommandName = "UPDATE_NEWS_PAGE&choosenNewsId=" + choosenNewsId;
        News choosenNews = null;
        if (choosenNewsId < 1) {
            path = "/WEB-INF/jsp/Error.jsp";
            lastCommandName = "UNKNOWN_COMMAND";
            session.setAttribute("path", lastCommandName);
            response.sendRedirect("Controller?command=" + path);
            return;
        }
        try {
            choosenNews = NEWS_SERVICE.getNews(choosenNewsId);

        } catch (ServiceException e) {
            lastCommandName = "UNKNOWN_COMMAND";
            session.setAttribute("path", lastCommandName);
            response.sendRedirect("Controller?command=" + lastCommandName);
            return;
        }
        request.setAttribute("choosenNews", choosenNews);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        request.getSession(true).setAttribute("path", lastCommandName);
        requestDispatcher.forward(request, response);
    }
}