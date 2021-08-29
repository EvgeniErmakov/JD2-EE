package by.newsportal.news.servise.impl;

import java.util.List;

import by.newsportal.news.bean.News;
import by.newsportal.news.dao.DAOProvider;
import by.newsportal.news.dao.NewsDAO;
import by.newsportal.news.dao.exception.DAOException;
import by.newsportal.news.servise.NewsServise;
import by.newsportal.news.servise.exception.ServiseException;

public class NewsServiseImpl implements NewsServise {

    private static final DAOProvider PROVIDER = DAOProvider.getInstance();
    private static final NewsDAO NEWS_DAO = PROVIDER.getNewsDAO();

    @Override
    public List<News> addNewses(int currentPageNumber) throws ServiseException {
        try {
            return NEWS_DAO.getNewsList(currentPageNumber);
        } catch (DAOException e) {
            throw new ServiseException();
        }

    }

    @Override
    public Integer getNewsMaxNumber() throws ServiseException {
        Integer newsMaxNumber;
        try {
            newsMaxNumber = NEWS_DAO.getNewsMaxNumber();
        } catch (DAOException e) {
            throw new ServiseException(e.getMessage(), e);
        }
        return newsMaxNumber;
    }

}
