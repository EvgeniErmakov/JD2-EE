package by.newsportal.news.service;

import by.newsportal.news.bean.RegistrationInfo;
import by.newsportal.news.bean.User;
import by.newsportal.news.service.exception.ServiceException;

public interface UserService {
    User authorization(RegistrationInfo info) throws ServiceException;
    User registration(RegistrationInfo info) throws ServiceException;
}
