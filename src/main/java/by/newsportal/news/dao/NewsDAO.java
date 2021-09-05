package by.newsportal.news.dao;

import java.util.List;


import by.newsportal.news.bean.News;
import by.newsportal.news.dao.exception.DAOException;
import by.newsportal.news.service.exception.ServiceException;

public interface NewsDAO {
    List<News> getNewsList(int currentPageNumber) throws DAOException;
    public void create(News entity) throws DAOException;
    Integer getNewsMaxNumber() throws DAOException;

    void update(News news) throws ServiceException, DAOException;

    News getNews(Integer chosenId) throws DAOException;

    void delete(Integer id) throws DAOException;
}
