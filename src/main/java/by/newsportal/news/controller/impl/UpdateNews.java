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
        System.out.println("Я тутка");
        HttpSession session = request.getSession(true);
        String lastCommandName = "AFTER_AUTHORIZATION";
       String path;
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Integer id = Integer.parseInt(request.getParameter("choosenId"));

        News news = new News(id, title, description);

        String message = "";

        try {
            System.out.println("Я тутка2");
            message = validateNews(news);
            NEWS_SERVICE.update(news);
            message = "News succesfully updated";
            request.setAttribute("message", message);
            path = "AFTER_AUTHORIZATION";
            session.setAttribute("lastURL", lastCommandName);
            response.sendRedirect("Controller?command=" + path);
            System.out.println("Я тутка5");
        } catch (ServiceException e) {
            path = "UPDATE_NEWS_PAGE&message=" + message;
            lastCommandName = "REGISTRATION_PAGE";
            session.setAttribute("lastURL", lastCommandName);
            response.sendRedirect("Controller?command=" + path);
        }
    }

    private String validateNews(News news) throws ServiceException {
        System.out.println("Я тутка3");
        String message = "";
        if (news.getDescription() == null || news.getDescription().equals("")) {
            System.out.println("Я тутка40");
            System.out.println(news.getDescription());
            message = "Field fullText is Empty";
            throw new ServiceException(message);
        }
        if (news.getTitle() == null || news.getTitle().equals("")) {
            System.out.println("Я тутка41");
            message = "Field title is Empty";
            throw new ServiceException(message);
        }
        System.out.println("Я тутка4");
        return message;

    }

}