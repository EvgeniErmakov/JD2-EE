package by.newsportal.news.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.newsportal.news.bean.RegistrationInfo;
import by.newsportal.news.bean.RoleEnum;
import by.newsportal.news.bean.User;
import by.newsportal.news.dao.UserDAO;
import by.newsportal.news.dao.connection.ConnectionPool;
import by.newsportal.news.dao.connection.ConnectionPoolException;
import by.newsportal.news.dao.exception.DAOException;

public class SQLUserDAO implements UserDAO {
    private static final String PARAMETR_NAME = "name";
    private static final String PARAMETR_SURNAME = "surname";
    private static final String PARAMETR_EMAIL = "email";
    private static final String PARAMETR_PASSWORD = "password";
    //  private static final String PARAMETR_ROLE = "role";
    private static final String PARAMETR_DATE = "Date";
    private static final String SQL_INSERT_USER = "INSERT INTO users(user_name, password, role) VALUES (?, ?, ?)";
    private static String SQL_GET_AUTHORIZATION = "SELECT * from ProjectNews WHERE(" + PARAMETR_EMAIL + "= ? AND " + PARAMETR_PASSWORD + "= ?)";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    ;

    {
        try {
            connectionPool.initPoolData();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User registration(RegistrationInfo information) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM projectnews");

            while (rs.next()) {
                String emailTab = rs.getString(4);
                if (information.getEmail().equals(emailTab)) {
                    return null;
                }
            }

            String sql = "INSERT INTO projectnews(name,surname,email,password) VALUES(?,?,?,?)";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, information.getName());
            ps.setString(2, information.getSurname());
            ps.setString(3, information.getEmail());
            ps.setString(4, information.getEnteredPassword());

            ps.executeUpdate();

            connectionPool.closeConnection(connection, st, rs);
            return new User(information.getEmail(), information.getEnteredPassword());
        } catch (SQLException e1) {

            throw new DAOException();
        } catch (ConnectionPoolException e) {

            throw new DAOException();
        }

    }

    @Override
    public User authorization(RegistrationInfo userInfo) throws DAOException {
        System.out.println("Vizov autorizacii");
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_GET_AUTHORIZATION);) {
            pr.setString(1, userInfo.getEmail());
            pr.setString(2, userInfo.getEnteredPassword());
            System.out.println("Remote DB connection established");
            ResultSet result = pr.executeQuery();

            if (result.next()) {
                String name = result.getString(PARAMETR_EMAIL);
                String surname = result.getString(PARAMETR_EMAIL);
                String email = result.getString(PARAMETR_EMAIL);
                String password = result.getString(PARAMETR_EMAIL);
                //   String roleString = result.getString(PARAMETR_ROLE);

                //  RoleEnum roleRole = RoleEnum.valueOf(roleString);
                user = new User(name, surname, email, password);
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException("Remote server could not be connected SQLException", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("False query", e);
        } catch (Exception e) {
            throw new DAOException("False query", e);
        }

    }
}
