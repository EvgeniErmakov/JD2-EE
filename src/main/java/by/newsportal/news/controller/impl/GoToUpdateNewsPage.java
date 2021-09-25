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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class GoToUpdateNewsPage implements Command {
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final GoToUpdateNewsPage INSTANCE = new GoToUpdateNewsPage();
    public static final String SESSION_PATH = "path";
    public static final String PATH_COMMAND_ERROR = "UNKNOWN_COMMAND";
    public static final String ERROR_PAGE = "Controller?command=/WEB-INF/jsp/error.jsp";
    public static final String UPDATE_NEWS_PAGE = "/WEB-INF/jsp/updateNewsPage.jsp";
    private static final Logger logger = LogManager.getLogger(GoToUpdateNewsPage.class);

    public GoToUpdateNewsPage() {
    }

    public static GoToUpdateNewsPage getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = UPDATE_NEWS_PAGE;
        HttpSession session = request.getSession(true);
        int choosenNewsId = Integer.parseInt(request.getParameter("choosenNewsId"));
        String lastCommandName = "UPDATE_NEWS_PAGE&choosenNewsId=" + choosenNewsId;
        News choosenNews;
        if (choosenNewsId < 1) {
            session.setAttribute(SESSION_PATH, PATH_COMMAND_ERROR);
            response.sendRedirect(ERROR_PAGE);
            return;
        }
        try {
            choosenNews = NEWS_SERVICE.getNews(choosenNewsId);

        } catch (ServiceException e) {
            logger.error("Error in the application", e);
            session.setAttribute(SESSION_PATH, PATH_COMMAND_ERROR);
            response.sendRedirect("Controller?command=" + PATH_COMMAND_ERROR);
            return;
        }
        request.setAttribute("choosenNews", choosenNews);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        request.getSession(true).setAttribute(SESSION_PATH, lastCommandName);
        requestDispatcher.forward(request, response);
    }
}