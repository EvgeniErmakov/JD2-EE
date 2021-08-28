package by.newsportal.news.servise;


import by.newsportal.news.servise.impl.NewsServiseImpl;
import by.newsportal.news.servise.impl.UserServiseImpl;

public final class ServiseProvider {
	private static final ServiseProvider INSTANCE = new ServiseProvider();
	
	private UserServise userServise = new UserServiseImpl();
	private NewsServise newsServise = new NewsServiseImpl();

	private ServiseProvider() {
		
	}
	
	public static ServiseProvider getInstance() {
		return INSTANCE;
	}

	public UserServise getUserServise() {
		return userServise;
	}
	
	public NewsServise getNewsServise() {
		return newsServise;
	}
}
