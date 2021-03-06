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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OfferNews implements Command {
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final OfferNews INSTANCE = new OfferNews();
    private static final String AFTER_AUTHORIZATION_PAGE = "Controller?command=GO_TO_AFTER_AUTHORIZATION_PAGE";
    private static final String NEWS_TITLE = "title";
    private static final String NEWS_DESCRIPTION = "description";
    private static final Logger logger = LogManager.getLogger(OfferNews.class);

    public OfferNews() {
    }

    public static OfferNews getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String title = request.getParameter(NEWS_TITLE);
        String description = request.getParameter(NEWS_DESCRIPTION);
        News news = new News(title, description);

        try {
            validateNews(news);
            NEWS_SERVICE.offerNews(news);
        } catch (ServiceException e) {
            logger.error("Field fullText or title is Empty", e);
        }finally {
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
