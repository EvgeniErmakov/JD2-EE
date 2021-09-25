package by.newsportal.news.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.newsportal.news.bean.News;
import by.newsportal.news.dao.NewsDAO;
import by.newsportal.news.dao.connection.ConnectionPool;
import by.newsportal.news.dao.connection.ConnectionPoolException;
import by.newsportal.news.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SQLNewsDAO implements NewsDAO {
    private static final String NEWS_ID = "id";
    private static final String NEWS_TITLE = "title";
    private static final String NEWS_DESCRIPTION = "description";
    private static final String NEWS_STATUS_OFFER = "status";
    private static final String SQL_GET_NUMBER_ROWS = "SELECT COUNT(*) FROM news WHERE status = '%s'";
    private static final String SQL_GET_NEWS_LIST = "SELECT * FROM news WHERE status = '%s' ORDER BY id DESC";
    private static final String SQL_GET_NEWS_BY_ID = "SELECT * FROM news WHERE(" + NEWS_ID + "=?)";
    private static final String SQL_UPDATE_NEWS = "UPDATE news SET  " + NEWS_TITLE + "=? , " + NEWS_DESCRIPTION + "= ? WHERE (" + NEWS_ID + "=?)";
    private static final String SQL_DELETE_NEWS = "UPDATE news SET " + NEWS_STATUS_OFFER + " = 'deleted' WHERE id = ?";
    private static final String SQL_INSERT_NEWS = "INSERT INTO news( " + NEWS_TITLE + ", " + NEWS_DESCRIPTION + ") VALUES (?, ?)";
    private static final String SQL_INSERT_OFFER_NEWS = "INSERT INTO news( " + NEWS_TITLE + ", " + NEWS_DESCRIPTION + ", " + NEWS_STATUS_OFFER + ") VALUES (?, ?, ?)";
    private static final String SQL_ADD_NEWS_TO_HOME_PAGE = "UPDATE news SET " + NEWS_STATUS_OFFER + " = 'published' WHERE id = ?";
    private static final String MESSAGE_LOGGER_NEWS_DELETED = "News has been deleted, id = ";
    private static final String MESSAGE_LOGGER_NEWS_CREATED = "News has been created, title: ";
    private static final String MESSAGE_LOGGER_NEWS_OFFERED = "News has been offered, title: ";
    private static final String MESSAGE_LOGGER_NEWS_UPDATED = " News has been updated, title: ";
    private static final String MESSAGE_LOGGER_NEWS_ADD_NEWS_TO_THE_MAIN_PAGE = " News has been added to the main page, id: ";
    private static final Logger logger = LogManager.getLogger(SQLNewsDAO.class);

    @Override
    public List<News> getNewsList(int currentPageNumber, String tableName) throws DAOException {
        int id;
        String title;
        String description;
        List<News> newsList = new ArrayList<>();
        String inquiry = String.format(SQL_GET_NEWS_LIST, tableName);
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet result = st.executeQuery(inquiry)) {
            result.absolute(currentPageNumber + (currentPageNumber - 1) * 4 - 1);
            for (int i = 5; i > 0; i--) {
                if (!result.next()) {
                    break;
                }
                id = result.getInt(NEWS_ID);
                title = result.getString(NEWS_TITLE);
                description = result.getString(NEWS_DESCRIPTION);
                newsList.add(new News(id, title, description));
            }
        } catch (SQLException e) {
            throw new DAOException("Remote server could 't be connected", e);
        } catch (Exception e) {
            throw new DAOException("False query", e);
        }

        return newsList;
    }

    @Override
    public Integer getNewsMaxNumber(String tableName) throws DAOException {
        int numberRow = 0;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(String.format(SQL_GET_NUMBER_ROWS, tableName));
             ResultSet rs = statement.executeQuery();) {
            while (rs.next()) {
                numberRow = rs.getInt("count(*)");
            }
        } catch (SQLException e) {
            throw new DAOException("Remote server could't be connected", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("False query", e);
        } catch (Exception e) {
            throw new DAOException("DAO Exception", e);
        }
        return numberRow;
    }

    @Override
    public void updateNews(News entity) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_UPDATE_NEWS);) {
            pr.setString(1, entity.getTitle());
            pr.setString(2, entity.getDescription());
            pr.setInt(3, entity.getId());
            pr.executeUpdate();
            logger.info(entity.getId() + MESSAGE_LOGGER_NEWS_UPDATED + entity.getTitle());
        } catch (SQLException e) {
            throw new DAOException("Remote server could't be connected", e);
        } catch (Exception e) {
            throw new DAOException("False query", e);
        }
    }

    public News getNews(Integer chosenId) throws DAOException {
        News news = null;
        int id;
        String title;
        String fullText;

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
            throw new DAOException("Remote server could't be connected", e);
        } catch (Exception e) {
            throw new DAOException("False query", e);
        }
        return news;
    }

    @Override
    public void deleteNews(Integer id) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_DELETE_NEWS);) {
            pr.setInt(1, id);
            pr.executeUpdate();
            logger.info(MESSAGE_LOGGER_NEWS_DELETED + id);
        } catch (SQLException e) {
            throw new DAOException("Remote server could't be connected", e);
        } catch (Exception e) {
            throw new DAOException("False query", e);
        }
    }

    @Override
    public void createNews(News entity) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_INSERT_NEWS)) {
            pr.setString(1, entity.getTitle());
            pr.setString(2, entity.getDescription());
            pr.executeUpdate();
            logger.info(MESSAGE_LOGGER_NEWS_CREATED + entity.getTitle());
        } catch (SQLException e) {
            throw new DAOException("Remote server could't be connected", e);
        } catch (Exception e) {
            throw new DAOException("False query", e);
        }
    }

    @Override
    public void offerNews(News entity) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_INSERT_OFFER_NEWS)) {
            pr.setString(1, entity.getTitle());
            pr.setString(2, entity.getDescription());
            pr.setString(3, "offered");
            pr.executeUpdate();
            logger.info(MESSAGE_LOGGER_NEWS_OFFERED + entity.getTitle());
        } catch (SQLException e) {
            throw new DAOException("Remote server could't be connected", e);
        } catch (Exception e) {
            throw new DAOException("False query", e);
        }
    }

    @Override
    public void addNewsToHomePage(Integer id) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_ADD_NEWS_TO_HOME_PAGE)) {
            pr.setInt(1, id);
            pr.executeUpdate();
            logger.info(MESSAGE_LOGGER_NEWS_ADD_NEWS_TO_THE_MAIN_PAGE + id);
        } catch (SQLException e) {
            throw new DAOException("Remote server could't be connected", e);
        } catch (Exception e) {
            throw new DAOException("False query", e);
        }
    }
}
