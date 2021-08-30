package by.newsportal.news.controller.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

public class AfterAuthorization implements Command {
	private static AfterAuthorization instance = new AfterAuthorization();
	public static final String SESSION_PATH = "path";
	public static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
	public static final String SESSION_PATH_COMMAND = "AFTER_AUTHORIZATION";
	public static final String ERROR_PAGE = "Controller?command=UNKNOWN_COMMAND";
	public static final String AFTER_AUTHORIZATION_PAGE = "/WEB-INF/jsp/AfterAuthorization.jsp";
	private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
	private AfterAuthorization() {
	}

	public static AfterAuthorization getInstance() {
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
		int currentPageNumber = 1;

		Integer pagesMaxNum = 1;
		List<Integer> numberOfPageList = null;
		HttpSession session = request.getSession(true);

		try {
			pagesMaxNum = NEWS_SERVICE.getNewsMaxNumber();
			pagesMaxNum = (pagesMaxNum % 5) > 0 ? pagesMaxNum / 5 + 1 : pagesMaxNum / 5;
			numberOfPageList = new ArrayList<>();
			for (Integer i = 1; i <= pagesMaxNum; i++) {
				numberOfPageList.add(i);
			}
			Collections.sort(numberOfPageList);
			currentPageNumber = pageNumberConverter(request.getParameter("requestCurrentPage"));

			session.setAttribute("currentPage", currentPageNumber);
			session.setAttribute("pageNumList", numberOfPageList);

			try {
				List<News> newses = NEWS_SERVICE.addNewses(currentPageNumber);
				session.setAttribute("newses", newses);
			} catch (ServiceException e) {
				e.printStackTrace();
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(ERROR_PAGE);
				requestDispatcher.forward(request, response);
				return;
			}
			request.getSession(true).setAttribute(SESSION_PATH, SESSION_PATH_COMMAND);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(AFTER_AUTHORIZATION_PAGE);
			requestDispatcher.forward(request, response);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}