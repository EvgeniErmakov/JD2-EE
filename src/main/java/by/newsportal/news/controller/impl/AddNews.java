package by.newsportal.news.controller.impl;

import java.io.IOException;

import by.newsportal.news.bean.News;
import by.newsportal.news.controller.Command;
import by.newsportal.news.service.NewsService;
import by.newsportal.news.service.ServiceProvider;
import by.newsportal.news.service.exception.ServiceException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddNews implements Command {
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final AddNews INSTANCE = new AddNews();

    public AddNews() {
    }

    public static AddNews getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String lastCommandName = "ADD_NEWS";
        String path = "AFTER_AUTHORIZATION";
        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            News news = new News(title, description);
            validateNews(news);
            NEWS_SERVICE.create(news);
            session.setAttribute("path", lastCommandName);
            response.sendRedirect("Controller?command=" + path);
        } catch (ServiceException e) {
            lastCommandName = "AFTER_AUTHORIZATION";
            session.setAttribute("path", lastCommandName);
            response.sendRedirect("Controller?command=" + path);
        }
    }

    private void validateNews(News news) throws ServiceException {
        String message = "";
        if (news.getDescription() == null || news.getDescription().equals("")) {
            message = "Field fullText is Empty";
            throw new ServiceException(message);
        }
        if (news.getTitle() == null || news.getTitle().equals("")) {
            message = "Field title is Empty";
            throw new ServiceException(message);
        }
    }
}
