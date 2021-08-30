package by.newsportal.news.dao;

import by.newsportal.news.dao.impl.SQLNewsDAO;
import by.newsportal.news.dao.impl.SQLUserDAO;

public class DAOProvider {
    private static final DAOProvider INSTANCE = new DAOProvider();
    private final UserDAO USER_DAO = new SQLUserDAO();
    private final NewsDAO NEWS_DAO = new SQLNewsDAO();

    private DAOProvider() {
    }

    public static DAOProvider getInstance() {
        return INSTANCE;
    }

    public UserDAO getUserDAO() {
        return USER_DAO;
    }

    public NewsDAO getNewsDAO() {
        return NEWS_DAO;
    }
}
