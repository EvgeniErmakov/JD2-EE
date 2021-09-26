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

public class UpdateNews implements Command {
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final UpdateNews INSTANCE = new UpdateNews();
    private static final String AFTER_AUTHORIZATION_PAGE = "Controller?command=AFTER_AUTHORIZATION";
    private static final String GO_TO_LIST_NEWS_OFFER_PAGE = "Controller?command=GO_TO_LIST_NEWS_OFFER_PAGE";
    private static final String AFTER_AUTHORIZATION = "AFTER_AUTHORIZATION";
    private static final String ERROR_PAGE = "Controller?command=UNKNOWN_COMMAND";
    private static final String GO_TO_LIST_NEWS_OFFER_PAGE_PATH = "GO_TO_LIST_NEWS_OFFER_PAGE";
    private static final String NEWS_TITLE = "title";
    private static final String NEWS_DESCRIPTION = "description";
    private static final String CHOOSEN_ID = "choosenId";
    private static final String SESSION_PATH = "from";
    private static final Logger logger = LogManager.getLogger(UpdateNews.class);

    public UpdateNews() {
    }

    public static UpdateNews getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter(NEWS_TITLE);
        String description = request.getParameter(NEWS_DESCRIPTION);
        int id = Integer.parseInt(request.getParameter(CHOOSEN_ID));
        News news = new News(id, title, description);
        String lastPage = (String) request.getSession(true).getAttribute(SESSION_PATH);

        try {
            validateNews(news);
            NEWS_SERVICE.updateNews(news);

            if (lastPage.equals(AFTER_AUTHORIZATION)) {
                response.sendRedirect(AFTER_AUTHORIZATION_PAGE);
            } else if (lastPage.equals(GO_TO_LIST_NEWS_OFFER_PAGE_PATH)) {
                response.sendRedirect(GO_TO_LIST_NEWS_OFFER_PAGE);
            }
        } catch (ServiceException e) {
            logger.error("Error in the application", e);
            response.sendRedirect(ERROR_PAGE);
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