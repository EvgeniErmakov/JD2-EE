package by.newsportal.news.controller.impl;

import java.io.IOException;
import by.newsportal.news.controller.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

public class GoToAuthorizationPage implements Command {
	private static final GoToAuthorizationPage instance = new GoToAuthorizationPage();
	public static final String AUTHORIZATION_PAGE = "/WEB-INF/jsp/AuthorizationPage.jsp";
	public static final String SESSION_PATH = "path";
	public static final String SESSION_PATH_COMMAND = "AUTHORIZATION_PAGE";

	public static GoToAuthorizationPage getInstance() {
		return instance;
	}
	
	private GoToAuthorizationPage() {}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession(true).setAttribute(SESSION_PATH, SESSION_PATH_COMMAND);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(AUTHORIZATION_PAGE);
		requestDispatcher.forward(request, response);
	}
}
