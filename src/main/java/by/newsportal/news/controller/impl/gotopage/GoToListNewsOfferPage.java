package by.newsportal.news.controller.impl.gotopage;

import by.newsportal.news.controller.Command;
import by.newsportal.news.service.NewsService;
import by.newsportal.news.service.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.newsportal.news.bean.News;
import by.newsportal.news.service.exception.ServiceException;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoToListNewsOfferPage implements Command {
    private static final GoToListNewsOfferPage INSTANCE = new GoToListNewsOfferPage();
    private static final String LIST_OFFER_NEWS_PAGE = "/WEB-INF/jsp/listOfferNewsPage.jsp";
    private static final String FROM_PATH = "from";
    private static final String GO_TO_ERROR_PAGE = "Controller?command=UNKNOWN_COMMAND";
    private static final String NEWS_STATUS = "offered";
    private static final String SESSION_PATH = "path";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String NAME_LIST_OF_NEWS = "newses";
    private static final String REQUEST_CURRENT_PAGE = "requestCurrentPage";
    private static final String GO_TO_LIST_NEWS_OFFER_PAGE = "GO_TO_LIST_NEWS_OFFER_PAGE";
    private static final String PAGE_NUMBER_LIST = "pageNumList";
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final Logger logger = LogManager.getLogger(GoToListNewsOfferPage.class);

    public static GoToListNewsOfferPage getInstance() {
        return INSTANCE;
    }

    private Integer pageNumberConverter(String currentPageNumber) {
        if (currentPageNumber == null || currentPageNumber.equals("")) {
            currentPageNumber = "1";
        }
        return Integer.parseInt(currentPageNumber);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currentPageNumber;

        Integer pagesMaxNum;
        List<Integer> numberOfPageList;
        HttpSession session = request.getSession(true);

        try {
            pagesMaxNum = NEWS_SERVICE.getNewsMaxNumber(NEWS_STATUS);
            pagesMaxNum = (pagesMaxNum % 5) > 0 ? pagesMaxNum / 5 + 1 : pagesMaxNum / 5;

            numberOfPageList = new ArrayList<>();
            for (int i = 1; i <= pagesMaxNum; i++) {
                numberOfPageList.add(i);
            }
            Collections.sort(numberOfPageList);
            currentPageNumber = pageNumberConverter(request.getParameter(REQUEST_CURRENT_PAGE));

            session.setAttribute(CURRENT_PAGE, currentPageNumber);
            session.setAttribute(PAGE_NUMBER_LIST, numberOfPageList);

            try {
                List<News> newses = NEWS_SERVICE.addNewses(currentPageNumber, NEWS_STATUS);
                session.setAttribute(NAME_LIST_OF_NEWS, newses);
            } catch (ServiceException e) {
                logger.error("Error in the application", e);
                response.sendRedirect(GO_TO_ERROR_PAGE);
                return;
            }
            request.getSession().setAttribute(SESSION_PATH, GO_TO_LIST_NEWS_OFFER_PAGE);
            request.getSession().setAttribute(FROM_PATH, GO_TO_LIST_NEWS_OFFER_PAGE);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(LIST_OFFER_NEWS_PAGE);
            requestDispatcher.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect(GO_TO_ERROR_PAGE);
            logger.error("Error in the application", e);
        }
    }
}