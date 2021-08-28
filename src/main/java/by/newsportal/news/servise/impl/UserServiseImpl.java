package by.newsportal.news.servise.impl;

import by.newsportal.news.bean.RegistrationInfo;
import by.newsportal.news.bean.User;
import by.newsportal.news.dao.DAOProvider;
import by.newsportal.news.dao.UserDAO;
import by.newsportal.news.dao.exception.DAOException;
import by.newsportal.news.servise.UserServise;
import by.newsportal.news.servise.exception.ServiseException;

public class UserServiseImpl implements UserServise {
	private static final DAOProvider PROVIDER = DAOProvider.getInstance();
	private static final UserDAO USER_DAO = PROVIDER.getUserDAO();

	@Override
	public User registration(RegistrationInfo info) throws ServiseException {
		try {
			return USER_DAO.registration(info);
		} catch (DAOException e) {
			throw new ServiseException();
		}
	}

	@Override
	public User authorization(RegistrationInfo info) throws ServiseException {
		try {
			return USER_DAO.authorization(info);
		} catch (DAOException e) {
			throw new ServiseException();
		}
	}
}
