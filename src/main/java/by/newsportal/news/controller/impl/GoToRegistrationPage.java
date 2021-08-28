package by.newsportal.news.controller.impl;

import java.io.IOException;
import by.newsportal.news.controller.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToRegistrationPage implements Command {
	private static GoToRegistrationPage instance = new GoToRegistrationPage();
	public static final String SESSION_PATH = "path";
	public static final String PATH_COMMAND_REG = "REGISTRATION_PAGE";
	public static final String REGISTRATION_PAGE = "/WEB-INF/jsp/RegistrationPage.jsp";

	private GoToRegistrationPage() {}

	public static GoToRegistrationPage getInstance() {
		return instance;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_REG);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(REGISTRATION_PAGE);
		requestDispatcher.forward(request, response);
	}
}
