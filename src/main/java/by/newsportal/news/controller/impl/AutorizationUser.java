package by.newsportal.news.controller.impl;

import java.io.IOException;
import by.newsportal.news.bean.RegistrationInfo;
import by.newsportal.news.bean.User;
import by.newsportal.news.controller.Command;
import by.newsportal.news.servise.ServiseProvider;
import by.newsportal.news.servise.UserServise;
import by.newsportal.news.servise.exception.ServiseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class AutorizationUser implements Command {
	private static AutorizationUser instance = new AutorizationUser();
	private static final ServiseProvider PROVIDER = ServiseProvider.getInstance();
	private static final UserServise USER_SERVISE = PROVIDER.getUserServise();
	public static final String SESSION_PATH = "path";
	public static final String PART_PATH = "Controller?command=";
	public static final String PATH_COMMAND_AUTHORIZATION = "AUTHORIZATION_PAGE";
	public static final String PATH_COMMAND_ERROR = "UNKNOWN_COMMAND";
	public static final String PATH_COMMAND_AFTER_AUTHORIZATION = "AFTER_AUTHORIZATION";
	

	private AutorizationUser() {
	}

	public static AutorizationUser getInstance() {
		return instance;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = PART_PATH + PATH_COMMAND_AUTHORIZATION;
		RegistrationInfo info = new RegistrationInfo(request);
		request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_AUTHORIZATION);	
	
		try {
			if(info.getEnteredPassword().equals("")) {
				path = PART_PATH + PATH_COMMAND_AUTHORIZATION + "&incorrect_data_message=Incorrect data:";
				response.sendRedirect(path);
				return;
			}

	User user = USER_SERVISE.authorization(info);
	
			request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_AFTER_AUTHORIZATION);
	request.getSession(true).setAttribute("user", user);
			path = PART_PATH + PATH_COMMAND_AFTER_AUTHORIZATION;
		if (user == null) {
		path =  PART_PATH + PATH_COMMAND_AUTHORIZATION + "&user_not_found=User is not found";
			} else {
				
			request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_AFTER_AUTHORIZATION);
			request.getSession(true).setAttribute("user", user);
				path = PART_PATH + PATH_COMMAND_AFTER_AUTHORIZATION;
			}
		} catch (ServiseException e) {
			request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_ERROR);
			path = PART_PATH + PATH_COMMAND_ERROR;
		} 
		response.sendRedirect(path);
	}
}
