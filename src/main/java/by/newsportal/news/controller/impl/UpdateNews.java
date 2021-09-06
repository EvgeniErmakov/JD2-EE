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

public class UpdateNews implements Command {
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final UpdateNews INSTANCE = new UpdateNews();

    public UpdateNews() {
    }

    public static UpdateNews getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String lastCommandName = "UPDATE_NEWS";
       String path;
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int id = Integer.parseInt(request.getParameter("choosenId"));

        News news = new News(id, title, description);

        try {
            validateNews(news);
            NEWS_SERVICE.update(news);
            path = "AFTER_AUTHORIZATION";
            System.out.println();
            session.setAttribute("path", lastCommandName);
            response.sendRedirect("Controller?command=" + path);
        } catch (ServiceException e) {
            path = "AFTER_AUTHORIZATION";
            lastCommandName = "UPDATE_NEWS";
            session.setAttribute("path", lastCommandName);
            response.sendRedirect("Controller?command=" + path);
        }
    }

    private void validateNews(News news) throws ServiceException {
        if (news.getDescription() == null || news.getDescription().equals("")) {
            System.out.println(news.getDescription());
            throw new ServiceException("Field fullText is Empty");
        }
        if (news.getTitle() == null || news.getTitle().equals("")) {
            throw new ServiceException("Field title is Empty");
        }


    }

}