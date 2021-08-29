package by.newsportal.news.service.impl;

import java.util.List;

import by.newsportal.news.bean.News;
import by.newsportal.news.dao.DAOProvider;
import by.newsportal.news.dao.NewsDAO;
import by.newsportal.news.dao.exception.DAOException;
import by.newsportal.news.service.NewsService;
import by.newsportal.news.service.exception.ServiceException;

public class NewsServiceImpl implements NewsService {

    private static final DAOProvider PROVIDER = DAOProvider.getInstance();
    private static final NewsDAO NEWS_DAO = PROVIDER.getNewsDAO();

    @Override
    public List<News> addNewses(int currentPageNumber) throws ServiceException {
        try {
            return NEWS_DAO.getNewsList(currentPageNumber);
        } catch (DAOException e) {
            throw new ServiceException();
        }

    }

    @Override
    public Integer getNewsMaxNumber() throws ServiceException {
        Integer newsMaxNumber;
        try {
            newsMaxNumber = NEWS_DAO.getNewsMaxNumber();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return newsMaxNumber;
    }

}
