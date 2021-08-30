package by.newsportal.news.dao;

import by.newsportal.news.bean.RegistrationInfo;
import by.newsportal.news.bean.User;
import by.newsportal.news.dao.exception.DAOException;

public interface UserDAO {
	User registration(RegistrationInfo info) throws DAOException;
	User authorization(RegistrationInfo info) throws DAOException;
}
