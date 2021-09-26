package by.newsportal.news.controller.impl;

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

import java.io.IOException;

public class AddNewsToHomePage implements Command {
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final AddNewsToHomePage INSTANCE = new AddNewsToHomePage();
    private static final String GO_TO_LIST_NEWS_OFFER_PAGE = "Controller?command=GO_TO_LIST_NEWS_OFFER_PAGE";
    private static final String ERROR_PAGE = "Controller?command=UNKNOWN_COMMAND";
    private static final String SESSION_PATH = "path";
    private static final String CHOOSEN_NEWS_ID = "choosenNewsId";
    private static final Logger logger = LogManager.getLogger(AddNewsToHomePage.class);

    public static AddNewsToHomePage getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int choosenNewsId = Integer.parseInt(request.getParameter(CHOOSEN_NEWS_ID));

        try {
            NEWS_SERVICE.addNewsToHomePage(choosenNewsId);
            response.sendRedirect(GO_TO_LIST_NEWS_OFFER_PAGE);
        } catch (ServiceException e) {
            logger.error("Error in the application", e);
            response.sendRedirect(ERROR_PAGE);
        }
    }
}