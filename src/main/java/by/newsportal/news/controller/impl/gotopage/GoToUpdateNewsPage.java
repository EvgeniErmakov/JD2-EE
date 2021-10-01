package by.newsportal.news.controller.impl.gotopage;

import by.newsportal.news.bean.News;
import by.newsportal.news.controller.Command;
import by.newsportal.news.service.NewsService;
import by.newsportal.news.service.ServiceProvider;
import by.newsportal.news.service.exception.ServiceException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class GoToUpdateNewsPage implements Command {
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final GoToUpdateNewsPage INSTANCE = new GoToUpdateNewsPage();
    private static final String SESSION_PATH = "path";
    private static final String CHOOSEN_NEWS = "choosenNews";
    private static final String CHOOSEN_NEWS_ID = "choosenNewsId";
    private static final String ERROR_PAGE = "Controller?command=/WEB-INF/jsp/error.jsp";
    private static final String UPDATE_NEWS_PAGE = "/WEB-INF/jsp/updateNewsPage.jsp";
    private static final String LAST_COMMAND_NAME = "UPDATE_NEWS_PAGE&choosenNewsId=";
    private static final Logger logger = LogManager.getLogger(GoToUpdateNewsPage.class);

    public GoToUpdateNewsPage() {
    }

    public static GoToUpdateNewsPage getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int choosenNewsId = Integer.parseInt(request.getParameter(CHOOSEN_NEWS_ID));
        News choosenNews;

        if (choosenNewsId < 1) {
            response.sendRedirect(ERROR_PAGE);
            return;
        }

        try {
            choosenNews = NEWS_SERVICE.getNews(choosenNewsId);
        } catch (ServiceException e) {
            logger.error("Error in the application", e);
            response.sendRedirect(ERROR_PAGE);
            return;
        }
        request.setAttribute(CHOOSEN_NEWS, choosenNews);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(UPDATE_NEWS_PAGE);
        request.getSession(true).setAttribute(SESSION_PATH, LAST_COMMAND_NAME + choosenNewsId);
        requestDispatcher.forward(request, response);
    }
}