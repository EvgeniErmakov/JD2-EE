package by.newsportal.news.controller.impl;

import java.io.IOException;
import by.newsportal.news.bean.User;
import by.newsportal.news.controller.Command;
import by.newsportal.news.servise.NewsServise;
import by.newsportal.news.servise.ServiseProvider;
import by.newsportal.news.servise.exception.ServiseException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AfterAuthorization implements Command{
	private static AfterAuthorization instance = new AfterAuthorization();
	
	public static final String SESSION_PATH = "path";
	public static final String PART_PATH = "Controller?command=";
	public static final String AUTHORIZATION_PAGE = "AUTHORIZATION_PAGE";
	public static final ServiseProvider PROVIDER = ServiseProvider.getInstance();
	public static final NewsServise NEWS_SERVISE = PROVIDER.getNewsServise();
	public static final String SESSION_PATH_COMMAND = "AFTER_AUTHORIZATION";
	public static final String ERROR_PAGE = "Controller?command=UNKNOWN_COMMAND";
	public static final String AFTER_AUTHORIZATION_PAGE = "/WEB-INF/jsp/AfterAuthorization.jsp";
	
	private AfterAuthorization() {}

	public static AfterAuthorization getInstance() {
		return instance;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
				
		if(session == null) {
			response.sendRedirect(PART_PATH + AUTHORIZATION_PAGE);
			return;
		}
		
		User user = (User) session.getAttribute("user");
		
	if(user == null) {
			response.sendRedirect(PART_PATH + AUTHORIZATION_PAGE);
		return;
	}
	try {
		request.setAttribute("newses", NEWS_SERVISE.addNewses());
	} catch (ServiseException e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(ERROR_PAGE);
		requestDispatcher.forward(request, response);
	}
		request.getSession(true).setAttribute(SESSION_PATH, SESSION_PATH_COMMAND);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(AFTER_AUTHORIZATION_PAGE);
		requestDispatcher.forward(request, response);	
	}
}
