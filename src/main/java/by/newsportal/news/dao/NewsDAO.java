package by.newsportal.news.dao;

import java.util.List;


import by.newsportal.news.bean.News;
import by.newsportal.news.dao.exception.DAOException;

public interface NewsDAO {
	
	List<News> getNewsList() throws DAOException;
}
