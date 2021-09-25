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
    public List<News> addNewses(int currentPageNumber, String tableName) throws ServiceException {
        try {
            return NEWS_DAO.getNewsList(currentPageNumber, tableName);
        } catch (DAOException e) {
            throw new ServiceException("NEWS_DAO Exception", e);
        }
    }

    @Override
    public Integer getNewsMaxNumber(String tableName) throws ServiceException {
        Integer newsMaxNumber;
        try {
            newsMaxNumber = NEWS_DAO.getNewsMaxNumber(tableName);
        } catch (DAOException e) {
            throw new ServiceException("NEWS_DAO Exception", e);
        }
        return newsMaxNumber;
    }

    @Override
    public void updateNews(News news) throws ServiceException {
        try {
            NEWS_DAO.updateNews(news);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public News getNews(Integer chosenId) throws ServiceException {
        News news;
        try {
            news = NEWS_DAO.getNews(chosenId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return news;
    }

    @Override
    public void deleteNews(Integer id) throws ServiceException {
        try {
            NEWS_DAO.deleteNews(id);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createNews(News news) throws ServiceException {
        try {
            NEWS_DAO.createNews(news);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void offerNews(News news) throws ServiceException {
        try {
            NEWS_DAO.offerNews(news);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void addNewsToHonePage(Integer id) throws ServiceException {
        try {
            NEWS_DAO.addNewsToHonePage(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}

