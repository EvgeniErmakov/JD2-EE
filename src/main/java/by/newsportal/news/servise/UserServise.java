package by.newsportal.news.servise;



import by.newsportal.news.bean.RegistrationInfo;
import by.newsportal.news.bean.User;
import by.newsportal.news.servise.exception.ServiseException;

public interface UserServise {
	User authorization(RegistrationInfo info) throws ServiseException;
	User registration(RegistrationInfo info) throws ServiseException;
}
