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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = "/WEB-INF/jsp/updateNewsPage.jsp";

        HttpSession session = request.getSession(true);
        Integer choosenNewsId = Integer.parseInt(request.getParameter("choosenNewsId"));
        String lastCommandName = "UPDATE_NEWS_PAGE&choosenNewsId=" + choosenNewsId;
        News choosenNews = null;
        if (choosenNewsId == null || choosenNewsId < 1) {
            path = "/WEB-INF/jsp/unknownPage.jsp";
            lastCommandName = "UNKNOWN_COMMAND";
            session.setAttribute("lastURL", lastCommandName); // for redirect in localization
            response.sendRedirect("Controller?commandToController=" + path);
            return;
        }

        try {
            choosenNews = NEWS_SERVICE.getNews(choosenNewsId);

        } catch (ServiceException e) {
            path = "/WEB-INF/jsp/unknownPage.jsp";
            lastCommandName = "UNKNOWN_COMMAND";
            session.setAttribute("lastURL", lastCommandName); // for redirect in localization
            response.sendRedirect("Controller?commandToController=" + lastCommandName);
            return;
        }

        request.setAttribute("choosenNews", choosenNews);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        request.getSession(true).setAttribute("lastURL", lastCommandName); // for redirect in localization
        requestDispatcher.forward(request, response);
    }
}