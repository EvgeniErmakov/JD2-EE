package by.newsportal.news.controller.impl;

import by.newsportal.news.controller.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class GoToOfferNewsPage implements Command {
    private static final GoToOfferNewsPage INSTANCE = new GoToOfferNewsPage();
    private static final String OFFER_NEWS_PAGE = "/WEB-INF/jsp/offerNewsPage.jsp";
    private static final String GO_TO_OFFER_NEWS_PAGE = "GO_TO_OFFER_NEWS_PAGE";
    private static final String SESSION_PATH = "path";

    public static GoToOfferNewsPage getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(OFFER_NEWS_PAGE);
        request.getSession(true).setAttribute(SESSION_PATH, GO_TO_OFFER_NEWS_PAGE);
        requestDispatcher.forward(request, response);
    }
}

