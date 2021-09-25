package by.newsportal.news.controller.impl;

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

public class GoToListNewsOfferPage implements Command {
    private static final GoToListNewsOfferPage INSTANCE = new GoToListNewsOfferPage();
    public static final String LIST_OFFER_NEWS_PAGE = "/WEB-INF/jsp/listOfferNewsPage.jsp";
    public static final String GO_TO_LIST_NEWS_OFFER = "GO_TO_LIST_NEWS_OFFER";
    public static final String ERROR_PAGE = "Controller?command=UNKNOWN_COMMAND";
    public static final String SESSION_PATH = "path";
    public static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();

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
            pagesMaxNum = NEWS_SERVICE.getNewsMaxNumber("offered");
            pagesMaxNum = (pagesMaxNum % 5) > 0 ? pagesMaxNum / 5 + 1 : pagesMaxNum / 5;

            numberOfPageList = new ArrayList<>();
            for (int i = 1; i <= pagesMaxNum; i++) {
                numberOfPageList.add(i);
            }
            Collections.sort(numberOfPageList);
            currentPageNumber = pageNumberConverter(request.getParameter("requestCurrentPage"));

            session.setAttribute("currentPage", currentPageNumber);
            session.setAttribute("pageNumList", numberOfPageList);

            try {
                List<News> newses = NEWS_SERVICE.addNewses(currentPageNumber, "offered");
                session.setAttribute("newses", newses);
            } catch (ServiceException e) {
                e.printStackTrace();
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(ERROR_PAGE);
                requestDispatcher.forward(request, response);
                return;
            }
            request.getSession(true).setAttribute(SESSION_PATH, GO_TO_LIST_NEWS_OFFER);
            request.getSession().setAttribute(SESSION_PATH, "GO_TO_LIST_NEWS_OFFER_PAGE");
            request.getSession().setAttribute("from", "GO_TO_LIST_NEWS_OFFER_PAGE");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(LIST_OFFER_NEWS_PAGE);
            requestDispatcher.forward(request, response);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}