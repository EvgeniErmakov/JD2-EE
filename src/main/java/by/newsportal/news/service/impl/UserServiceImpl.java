package by.newsportal.news.service.impl;

import by.newsportal.news.bean.RegistrationInfo;
import by.newsportal.news.bean.User;
import by.newsportal.news.dao.DAOProvider;
import by.newsportal.news.dao.UserDAO;
import by.newsportal.news.dao.exception.DAOException;
import by.newsportal.news.service.UserService;
import by.newsportal.news.service.exception.ServiceException;

public class UserServiceImpl implements UserService {
    private static final DAOProvider PROVIDER = DAOProvider.getInstance();
    private static final UserDAO USER_DAO = PROVIDER.getUserDAO();

    @Override
    public User registration(RegistrationInfo info) throws ServiceException {
        try {
            return USER_DAO.registration(info);
        } catch (DAOException e) {
            throw new ServiceException("User_DAO Exception", e);
        }
    }

    @Override
    public User authorization(RegistrationInfo info) throws ServiceException {
        try {
            return USER_DAO.authorization(info);
        } catch (DAOException e) {
            throw new ServiceException("User_DAO Exception", e);
        }
    }
}
