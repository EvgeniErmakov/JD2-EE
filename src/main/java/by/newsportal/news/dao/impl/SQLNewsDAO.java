package by.newsportal.news.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import by.newsportal.news.bean.News;
import by.newsportal.news.dao.NewsDAO;
import by.newsportal.news.dao.connection.ConnectionPool;
import by.newsportal.news.dao.connection.ConnectionPoolException;
import by.newsportal.news.dao.exception.DAOException;

public class SQLNewsDAO implements NewsDAO {
    private static final String NEWS_ID = "id";
    private static final String NEWS_TITLE = "title";
    private static final String NEWS_DESCRIPTION = "description";
    private static final String SQL_GET_NUMBER_ROWS = "select count(*) from news";
    private static final String SQL_GET_NEWS_LIST = "SELECT * FROM news";
    private static final String SQL_GET_NEWS_BY_ID = "SELECT * FROM news WHERE(" + NEWS_ID + "=?)";
    private static final String SQL_UPDATE_NEWS = "UPDATE news SET  " + NEWS_TITLE + "=? , " + NEWS_DESCRIPTION + "= ? WHERE (" + NEWS_ID + "=?)";

    @Override
    public List<News> getNewsList(int currentPageNumber) throws DAOException {
        int id;
        String title;
        String description;
        List<News> newsList = new ArrayList<News>();
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet result = st.executeQuery(SQL_GET_NEWS_LIST);) {
            result.absolute(currentPageNumber + (currentPageNumber - 1) * 4 - 1);
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
        } catch (Exception e) {
            throw new DAOException("False query", e);
        }
        return newsList;
    }

    @Override
    public Integer getNewsMaxNumber() throws DAOException {
        int numberRow = 0;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_NUMBER_ROWS);
             ResultSet rs = statement.executeQuery();) {
            while (rs.next()) {
                numberRow = rs.getInt("count(*)");
            }
        } catch (SQLException e) {
            throw new DAOException("Remote server could not be connected", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("False query", e);
        } catch (Exception e) {
            throw new DAOException("DAO Exception", e);
        }
        return numberRow;
    }

    @Override
    public void update(News entity) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_UPDATE_NEWS);) {
            pr.setString(1, entity.getTitle());
            pr.setString(2, entity.getDescription());
            pr.setInt(3, entity.getId());
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Remote server could not be connected", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("False query", e);
        } catch (Exception e) {
            throw new DAOException("False query", e);

        }
    }

    public News getNews(Integer chosenId) throws DAOException {
        News news = null;

        Integer id;
        String title;
        String fullText;
        String brief;
        String imgLink;

        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement st = connection.prepareStatement(SQL_GET_NEWS_BY_ID);) {
            st.setInt(1, chosenId);
            ResultSet result = st.executeQuery();
            while (result.next()) {

                id = result.getInt(NEWS_ID);
                title = result.getString(NEWS_TITLE);
                fullText = result.getString(NEWS_DESCRIPTION);
                news = new News(id, title, fullText);
            }

        } catch (SQLException e) {
            throw new DAOException("Remote server could not be connected", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("False query", e);
        } catch (Exception e) {
            throw new DAOException("False query", e);
        }

        return news;
    }
}
