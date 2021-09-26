package by.newsportal.news.dao;

import java.util.List;

import by.newsportal.news.bean.News;
import by.newsportal.news.dao.exception.DAOException;

public interface NewsDAO {
    List<News> getNewsList(int currentPageNumber, String tableName) throws DAOException;

    void createNews(News entity) throws DAOException;

    void offerNews(News entity) throws DAOException;

    void addNewsToHomePage(Integer id) throws DAOException;

    Integer getNewsMaxNumber(String tableName) throws DAOException;

    void updateNews(News news) throws DAOException;

    News getNews(Integer chosenId) throws DAOException;

    void deleteNews(Integer id) throws DAOException;

}
