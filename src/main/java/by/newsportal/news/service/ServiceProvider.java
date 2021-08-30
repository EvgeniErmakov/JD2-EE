package by.newsportal.news.service;

import by.newsportal.news.service.impl.NewsServiceImpl;
import by.newsportal.news.service.impl.UserServiceImpl;

public final class ServiceProvider {
	private static final ServiceProvider INSTANCE = new ServiceProvider();
	
	private UserService userService = new UserServiceImpl();
	private NewsService newsService = new NewsServiceImpl();

	private ServiceProvider() {
	}
	
	public static ServiceProvider getInstance() {
		return INSTANCE;
	}

	public UserService getUserService() {
		return userService;
	}
	
	public NewsService getNewsService() {
		return newsService;
	}
}
