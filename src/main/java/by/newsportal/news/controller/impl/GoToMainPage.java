package by.newsportal.news.controller.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.newsportal.news.bean.News;
import by.newsportal.news.controller.Command;
import by.newsportal.news.servise.NewsServise;
import by.newsportal.news.servise.ServiseProvider;
import by.newsportal.news.servise.exception.ServiseException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToMainPage implements Command {
    public static final ServiseProvider PROVIDER = ServiseProvider.getInstance();
    public static final NewsServise NEWS_SERVISE = PROVIDER.getNewsServise();
    public static final String SESSION_PATH = "path";
    public static final String PATH_COMMAND_MAIN = "go_to_main_page";
    public static final String MAIN_PAGE = "/WEB-INF/jsp/main.jsp";
    public static final String ERROR_PAGE = "Controller?command=UNKNOWN_COMMAND";
    private static final NewsServise NEWS_SERVICE = PROVIDER.getNewsServise();



    private static GoToMainPage instance = new GoToMainPage();

    private GoToMainPage() {
    }

    public static GoToMainPage getInstance() {
        return instance;
    }

    private Integer pageNumberConverter(String currentPageNumber) {

        if (currentPageNumber == null || currentPageNumber.equals("")) {
            currentPageNumber = "1";
        }


        return Integer.parseInt(currentPageNumber);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer currentPageNumber;

        Integer pagesMaxNum = 1;
        List<Integer> numberOfPageList = null;
        HttpSession session = request.getSession(true);


        try {
            pagesMaxNum = NEWS_SERVICE.getNewsMaxNumber();
            pagesMaxNum = (pagesMaxNum % 5) > 0 ? pagesMaxNum / 5 + 1 : pagesMaxNum / 5;


            numberOfPageList = new ArrayList<Integer>();
            for (Integer i = 1; i <= pagesMaxNum; i++) {
                numberOfPageList.add(i);
            }
            Collections.sort(numberOfPageList);
            currentPageNumber = pageNumberConverter(request.getParameter("requestCurrentPage"));
            if (!numberOfPageList.contains(currentPageNumber)) {
                currentPageNumber = 1;
            }
            session.setAttribute("currentPage", currentPageNumber);
            session.setAttribute("pageNumList", numberOfPageList);


            try {
                List<News> newses = NEWS_SERVICE.addNewses(currentPageNumber);
                session.setAttribute("newses", newses);
            } catch (ServiseException e) {
                e.printStackTrace();
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(ERROR_PAGE);
                requestDispatcher.forward(request, response);
                return;
            }

            request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_MAIN);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(MAIN_PAGE);
            requestDispatcher.forward(request, response);
        } catch (ServiseException e) {
            e.printStackTrace();
        }
    }
}
