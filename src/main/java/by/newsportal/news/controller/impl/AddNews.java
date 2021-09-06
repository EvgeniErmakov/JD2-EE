package by.newsportal.news.controller.impl;

import java.io.IOException;

import by.newsportal.news.bean.News;
import by.newsportal.news.controller.Command;
import by.newsportal.news.service.NewsService;
import by.newsportal.news.service.ServiceProvider;
import by.newsportal.news.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddNews implements Command {
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final AddNews INSTANCE = new AddNews();
    public static final String AFTER_AUTHORIZATION_PAGE = "Controller?command=AFTER_AUTHORIZATION";
    public static final String SESSION_PATH_COMMAND = "AFTER_AUTHORIZATION";
    public AddNews() {
    }

    public static AddNews getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            News news = new News(title, description);
            validateNews(news);
            NEWS_SERVICE.create(news);
        } catch (ServiceException e) {
            e.printStackTrace();
        } finally {
            session.setAttribute("path", SESSION_PATH_COMMAND);
            response.sendRedirect(AFTER_AUTHORIZATION_PAGE);
        }
    }

    private void validateNews(News news) throws ServiceException {
        if (news.getDescription() == null || news.getDescription().equals("")) {
            throw new ServiceException("Field fullText is Empty");
        }
        if (news.getTitle() == null || news.getTitle().equals("")) {
            throw new ServiceException("Field title is Empty");
        }
    }
}
