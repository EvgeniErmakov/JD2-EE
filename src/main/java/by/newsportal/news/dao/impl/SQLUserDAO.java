package by.newsportal.news.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import by.newsportal.news.bean.RegistrationInfo;
import by.newsportal.news.bean.RoleEnum;
import by.newsportal.news.bean.User;
import by.newsportal.news.dao.UserDAO;
import by.newsportal.news.dao.connection.ConnectionPool;
import by.newsportal.news.dao.connection.ConnectionPoolException;
import by.newsportal.news.dao.exception.DAOException;

public class SQLUserDAO implements UserDAO {
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";
    private static final String SQL_INSERT_USER = "INSERT INTO projectnews(name,surname,email,password,role) VALUES(?,?,?,?,?)";
    private static String SQL_GET_AUTHORIZATION = "SELECT * FROM projectnews WHERE(" + EMAIL + "= ? AND " + PASSWORD + "= ?)";
    private static final String SELECT_USER = "SELECT * FROM projectnews WHERE(" + EMAIL + "= ?)";
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    {
        try {
            connectionPool.initPoolData();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User registration(RegistrationInfo information) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement insertUser = connection.prepareStatement(SQL_INSERT_USER);
             PreparedStatement selectUser = connection.prepareStatement(SELECT_USER)) {

            selectUser.setString(1, information.getEmail());
            ResultSet result = selectUser.executeQuery();

            if (result.next()) {
                return null;
            } else {
                insertUser.setString(1, information.getName());
                insertUser.setString(2, information.getSurname());
                insertUser.setString(3, information.getEmail());
                insertUser.setString(4, information.getEnteredPassword());
                insertUser.setString(5, RoleEnum.USER.getRole());
                insertUser.executeUpdate();
                return new User(information.getEmail(), information.getEnteredPassword());
            }
        } catch (SQLException e) {
            throw new DAOException("SQLException", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPoolException", e);
        }
    }

    @Override
    public User authorization(RegistrationInfo userInfo) throws DAOException {
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_GET_AUTHORIZATION);) {
            pr.setString(1, userInfo.getEmail());
            pr.setString(2, userInfo.getEnteredPassword());
            ResultSet result = pr.executeQuery();
            if (result.next()) {
                String name = result.getString(NAME);
                String surname = result.getString(SURNAME);
                String email = result.getString(EMAIL);
                String password = result.getString(PASSWORD);
                RoleEnum role = RoleEnum.valueOf(result.getString(ROLE));
                user = new User(name, surname, email, password, role);
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException("Remote server could't be connected SQLException", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("False query", e);
        } catch (Exception e) {
            throw new DAOException("False query", e);
        }
    }
}
