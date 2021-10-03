package by.newsportal.news.controller.impl;

import java.io.IOException;

import by.newsportal.news.controller.Command;
import by.newsportal.news.service.NewsService;
import by.newsportal.news.service.ServiceProvider;
import by.newsportal.news.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteNews implements Command {
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final DeleteNews INSTANCE = new DeleteNews();
    private static final String AFTER_AUTHORIZATION_PAGE = "Controller?command=GO_TO_AFTER_AUTHORIZATION_PAGE";
    private static final String ERROR_PAGE = "Controller?command=UNKNOWN_COMMAND";
    private static final String GO_TO_LIST_NEWS_OFFER_PAGE_COMMAND = "Controller?command=GO_TO_LIST_NEWS_OFFER_PAGE";
    private static final String SESSION_PATH_COMMAND = "GO_TO_AFTER_AUTHORIZATION_PAGE";
    private static final String GO_TO_LIST_NEWS_OFFER_PAGE = "GO_TO_LIST_NEWS_OFFER_PAGE";
    private static final String LAST_PAGE = "from";
    private static final String CHOOSEN_NEWS_ID = "choosenNewsId";
    private static final Logger logger = LogManager.getLogger(DeleteNews.class);

    public static DeleteNews getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int choosenNewsId = Integer.parseInt(request.getParameter(CHOOSEN_NEWS_ID));
        String lastPage = (String) request.getSession(true).getAttribute(LAST_PAGE);

        try {
            NEWS_SERVICE.deleteNews(choosenNewsId);
            if (lastPage.equals(SESSION_PATH_COMMAND)) {
                response.sendRedirect(AFTER_AUTHORIZATION_PAGE);
            } else if (lastPage.equals(GO_TO_LIST_NEWS_OFFER_PAGE)) {
                response.sendRedirect(GO_TO_LIST_NEWS_OFFER_PAGE_COMMAND);
            }
        } catch (ServiceException e) {
            logger.error("Error in the application", e);
            response.sendRedirect(ERROR_PAGE);
        }
    }
}