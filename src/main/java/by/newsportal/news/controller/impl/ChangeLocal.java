package by.newsportal.news.controller.impl;

import java.io.IOException;
import by.newsportal.news.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChangeLocal implements Command {
	private static final ChangeLocal instance = new ChangeLocal();
	public static final String SESSION_PATH = "path";
	public static final String SESSION_LOCAL = "local";
	public static final String PART_PATH = "Controller?command=";
	
	private ChangeLocal() {}

	public static ChangeLocal getInstance() {
		return instance;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession(true).setAttribute(SESSION_LOCAL, request.getParameter(SESSION_LOCAL));
		String path = PART_PATH + (String)request.getSession(true).getAttribute(SESSION_PATH);
		response.sendRedirect(path);
	}
}
