package by.newsportal.news.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


import by.newsportal.news.bean.News;
import by.newsportal.news.dao.NewsDAO;
import by.newsportal.news.dao.connection.ConnectionPool;
import by.newsportal.news.dao.connection.ConnectionPoolException;
import by.newsportal.news.dao.exception.DAOException;

public class SQLNewsDAO implements NewsDAO {
    private static final String NEWS_ID = "id";
    private static final String NEWS_TITLE = "title";
    private static final String NEWS_DESCRIPTION = "description";
    //private static final String SQL_INSERT_NEWS = "INSERT INTO news( " + PARAM_TITLE + ", " + PARAM_FULL_TEXT + ", "
        //    + PARAM_BRIEF + ", " + PARAM_IMG_LINK + ") VALUES (?, ?, ?, ?)";
    private static final String SQL_GET_NUMBER_ROWS = "select count(*) from news";
    private static final String SQL_GET_NEWS_LIST = "SELECT * FROM news";
    private static final String SQL_DELETE_NEWS = "DELETE FROM news WHERE(" + NEWS_ID + "=?)";


    @Override
    public List<News> getNewsList() throws DAOException {
        Integer id;
        String title;
        String description;
        List<News> newsList = new ArrayList<News>();
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet result = st.executeQuery(SQL_GET_NEWS_LIST);) {
            result.absolute(1);
            for (int i = 0; i < 5; i++) {
                if (!result.next()) {
                    break;
                }
                id = result.getInt(NEWS_ID);
                title = result.getString(NEWS_TITLE);
                description = result.getString(NEWS_DESCRIPTION);
                newsList.add(new News(id, title, description));
            }
        } catch (SQLException e) {
            throw new DAOException("Remote server could not be connected", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("False query", e);
        } catch (Exception e) {
            throw new DAOException("False query", e);
        }
        return newsList;
    }
}
