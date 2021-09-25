package by.newsportal.news.controller.impl;

import java.io.IOException;

import by.newsportal.news.bean.News;
import by.newsportal.news.controller.Command;
import by.newsportal.news.service.NewsService;
import by.newsportal.news.service.ServiceProvider;
import by.newsportal.news.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OfferNews implements Command {
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final OfferNews INSTANCE = new OfferNews();
    public static final String AFTER_AUTHORIZATION_PAGE = "Controller?command=AFTER_AUTHORIZATION";
    public static final String SESSION_PATH_COMMAND = "AFTER_AUTHORIZATION";
    public static final String NEWS_ID = "id";
    public static final String NEWS_TITLE = "title";
    public static final String NEWS_DESCRIPTION = "description";
    public static final String SESSION_PATH = "path";
    private static final Logger logger = LogManager.getLogger(OfferNews.class);

    public OfferNews() {
    }

    public static OfferNews getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        try {
            String title = request.getParameter(NEWS_TITLE);
            String description = request.getParameter(NEWS_DESCRIPTION);
            News news = new News(title, description);
            validateNews(news);
            NEWS_SERVICE.offerNews(news);
        } catch (ServiceException e) {
            logger.error("Error in the application", e);
        } finally {
            session.setAttribute(SESSION_PATH, SESSION_PATH_COMMAND);
            response.sendRedirect(AFTER_AUTHORIZATION_PAGE);
        }
    }

    private void validateNews(News news) throws ServiceException {
        if (news.getDescription() == null || news.getDescription().equals("")) {
            throw new ServiceException("Field fullText is Empty");
        }
        if (news.getTitle() == null || news.getTitle().equals("")) {
            throw new ServiceException("Field title is Empty");
        }
    }
}
