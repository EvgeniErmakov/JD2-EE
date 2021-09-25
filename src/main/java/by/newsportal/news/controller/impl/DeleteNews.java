package by.newsportal.news.controller.impl;

import java.io.IOException;

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

public class DeleteNews implements Command {
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final DeleteNews INSTANCE = new DeleteNews();
    public static final String AFTER_AUTHORIZATION_PAGE = "Controller?command=AFTER_AUTHORIZATION";
    public static final String ERROR_PAGE = "Controller?command=/WEB-INF/jsp/error.jsp";
    public static final String PATH_COMMAND_ERROR = "UNKNOWN_COMMAND";
    public static final String GO_TO_LIST_NEWS_OFFER_PAGE_COMMAND = "Controller?command=GO_TO_LIST_NEWS_OFFER_PAGE";
    public static final String SESSION_PATH_COMMAND = "AFTER_AUTHORIZATION";
    public static final String SESSION_PATH = "path";
    public static final String GO_TO_LIST_NEWS_OFFER_PAGE = "GO_TO_LIST_NEWS_OFFER_PAGE";
    private static final Logger logger = LogManager.getLogger(DeleteNews.class);

    public static DeleteNews getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int choosenNewsId = Integer.parseInt(request.getParameter("choosenNewsId"));

        if (choosenNewsId < 1) {
            session.setAttribute(SESSION_PATH, PATH_COMMAND_ERROR);
            response.sendRedirect(ERROR_PAGE);
            return;
        }
        try {
            NEWS_SERVICE.deleteNews(choosenNewsId);
            if (request.getSession(true).getAttribute("from").equals(SESSION_PATH_COMMAND)) {
                response.sendRedirect(AFTER_AUTHORIZATION_PAGE);
            } else if (request.getSession(true).getAttribute("from").equals(GO_TO_LIST_NEWS_OFFER_PAGE)) {
                response.sendRedirect(GO_TO_LIST_NEWS_OFFER_PAGE_COMMAND);
            }
        } catch (ServiceException e) {
            logger.error("Error in the application", e);
        }
    }
}