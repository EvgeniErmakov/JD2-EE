package by.newsportal.news.service;

import java.util.List;

import by.newsportal.news.bean.News;
import by.newsportal.news.service.exception.ServiceException;

public interface NewsService {
    List<News> addNewses(int currentPageNumber, String tableName) throws ServiceException;

    void createNews(News news) throws ServiceException;

    void offerNews(News news) throws ServiceException;

    void addNewsToHomePage(Integer id) throws ServiceException;

    Integer getNewsMaxNumber(String tableName) throws ServiceException;

    void updateNews(News news) throws ServiceException;

    News getNews(Integer chosenId) throws ServiceException;

    public void deleteNews(Integer id) throws ServiceException;
}
