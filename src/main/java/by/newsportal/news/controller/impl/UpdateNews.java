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
        String lastCommandName = "AFTER_AUTHORIZATION";
       String path;
        String title = request.getParameter("title");
        String fullText = request.getParameter("description");
        Integer id = Integer.parseInt(request.getParameter("choosenId"));
        News news = new News(id, title, fullText);

        String message = "";

        try {
            message = validateNews(news);
            NEWS_SERVICE.update(news);
            message = "News succesfully updated";
            request.setAttribute("message", message);
            path = "AFTER_AUTHORIZATION";
            session.setAttribute("lastURL", lastCommandName); //for redirect in localization
            response.sendRedirect("Controller?command=" + path);
        } catch (ServiceException e) {
            path = "UPDATE_NEWS_PAGE&message=" + message;
            lastCommandName = "REGISTRATION_PAGE";
            session.setAttribute("lastURL", lastCommandName); //for redirect in localization
            response.sendRedirect("Controller?command=" + path);
        }
    }

    private String validateNews(News news) throws ServiceException {
        String message = "";
        if (news.getDescription() == null || news.getDescription().equals("")) {
            message = "Field fullText is Empty";
            throw new ServiceException(message);
        }
        if (news.getTitle() == null || news.getTitle().equals("")) {
            message = "Field title is Empty";
            throw new ServiceException(message);
        }
        return message;

    }

}