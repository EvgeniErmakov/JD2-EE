package by.newsportal.news.servise;

import java.util.List;

import by.newsportal.news.bean.News;
import by.newsportal.news.servise.exception.ServiseException;

public interface NewsServise {
	List<News> addNewses(int currentPageNumber) throws ServiseException;

	public Integer getNewsMaxNumber() throws ServiseException;


}
