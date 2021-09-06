package by.newsportal.news.service;

import by.newsportal.news.service.impl.NewsServiceImpl;
import by.newsportal.news.service.impl.UserServiceImpl;

public final class ServiceProvider {
    private static final ServiceProvider INSTANCE = new ServiceProvider();
    private final UserService USER_SERVICE = new UserServiceImpl();
    private final NewsService NEWS_SERVICE = new NewsServiceImpl();

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return USER_SERVICE;
    }

    public NewsService getNewsService() {
        return NEWS_SERVICE;
    }
}
