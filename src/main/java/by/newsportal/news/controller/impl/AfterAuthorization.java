package by.newsportal.news.controller.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class AfterAuthorization implements Command {
    private static final AfterAuthorization INSTANCE = new AfterAuthorization();
    private static final String SESSION_PATH = "path";
    private static final String FROM_PATH = "from";
    private static final String NEWS_STATUS = "published";
    private static final String NAME_LIST_OF_NEWS = "newses";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String REQUEST_CURRENT_PAGE = "requestCurrentPage";
    private static final String PAGE_NUMBER_LIST = "pageNumList";
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final String SESSION_PATH_COMMAND = "AFTER_AUTHORIZATION";
    private static final String ERROR_PAGE = "Controller?command=UNKNOWN_COMMAND";
    private static final String AFTER_AUTHORIZATION_PAGE = "/WEB-INF/jsp/afterAuthorization.jsp";
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final Logger logger = LogManager.getLogger(AfterAuthorization.class);

    private AfterAuthorization() {
    }

    public static AfterAuthorization getInstance() {
        return INSTANCE;
    }

    public Integer pageNumberConverter(String currentPageNumber) {
        if (currentPageNumber == null || currentPageNumber.equals("")) {
            currentPageNumber = "1";
        }
        return Integer.parseInt(currentPageNumber);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currentPageNumber;
        int pagesMaxNum;
        List<Integer> numberOfPageList;
        HttpSession session = request.getSession(true);

        try {
            pagesMaxNum = NEWS_SERVICE.getNewsMaxNumber(NEWS_STATUS);
            pagesMaxNum = (pagesMaxNum % 5) > 0 ? pagesMaxNum / 5 + 1 : pagesMaxNum / 5;
            numberOfPageList = new ArrayList<>();
            for (int i = 1; i <= pagesMaxNum; i++) {
                numberOfPageList.add(i);
            }

            currentPageNumber = pageNumberConverter(request.getParameter(REQUEST_CURRENT_PAGE));

            session.setAttribute(CURRENT_PAGE, currentPageNumber);
            session.setAttribute(PAGE_NUMBER_LIST, numberOfPageList);

            try {
                List<News> newses = NEWS_SERVICE.addNewses(currentPageNumber, NEWS_STATUS);
                session.setAttribute(NAME_LIST_OF_NEWS, newses);
            } catch (ServiceException e) {
                logger.error("Error in the application", e);
                response.sendRedirect(ERROR_PAGE);
                return;
            }
            request.getSession().setAttribute(SESSION_PATH, SESSION_PATH_COMMAND);
            request.getSession().setAttribute(FROM_PATH, SESSION_PATH_COMMAND);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(AFTER_AUTHORIZATION_PAGE);
            requestDispatcher.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE);
            logger.error("Error in the application", e);
        }
    }
}