package by.newsportal.news.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import by.newsportal.news.bean.RegistrationInfo;
import by.newsportal.news.bean.User;
import by.newsportal.news.dao.UserDAO;
import by.newsportal.news.dao.connection.ConnectionPool;
import by.newsportal.news.dao.connection.ConnectionPoolException;
import by.newsportal.news.dao.exception.DAOException;

public class SQLUserDAO implements UserDAO {

	private ConnectionPool connectionPool = ConnectionPool.getInstance();;
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
	public User authorization(RegistrationInfo info) throws DAOException {
		try {
			User user = null;
			Connection con = connectionPool.takeConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM ProjectNews");
			while (rs.next()) {
				if((info.getEmail().equals(rs.getString(3)))&&(info.getEnteredPassword().equals(rs.getString(4)))) {
					user = new User(rs.getString("name"), rs.getString("surname"), rs.getString("Email"), rs.getString("password"));
				}
			}

			connectionPool.closeConnection(con, st, rs);
			return user;
		} catch (SQLException e1) {

			throw new DAOException();
		} catch (ConnectionPoolException e) {

			throw new DAOException();
		}
	}
}
