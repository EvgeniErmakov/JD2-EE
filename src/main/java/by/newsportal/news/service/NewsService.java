package by.newsportal.news.service;

import java.util.List;

import by.newsportal.news.bean.News;
import by.newsportal.news.service.exception.ServiceException;

public interface NewsService {
	List<News> addNewses(int currentPageNumber) throws ServiceException;

	public Integer getNewsMaxNumber() throws ServiceException;


}