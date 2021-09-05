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
    private static final ServiceProvider PROVIDER=ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final AddNews INSTANCE = new AddNews();

    public AddNews() {
    }

    public static AddNews getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Я тут 1");
        HttpSession session=request.getSession(false);
        String lastCommandName="ADD_NEWS";
        String path;
        String title=request.getParameter("title");
        String description=request.getParameter("description");
        News news=new News(title, description);
        String message="";
        System.out.println("Я тут 2");
        try {
            message= validateNews(news);
            NEWS_SERVICE.create(news);
            message="News succesfully added";
            request.setAttribute("message", message);
            System.out.println("Я тут 3");
            path="AFTER_AUTHORIZATION&message=Registration complite, please log in";
            session.setAttribute("lastURL", lastCommandName );
            response.sendRedirect("Controller?command="+path);
            System.out.println("Я тут 4");
        }
        catch (ServiceException e) {
            System.out.println("Я тут 5");
            path="AFTER_AUTHORIZATION&message="+message;
            lastCommandName="AFTER_AUTHORIZATION";
            session.setAttribute("lastURL", lastCommandName);
            RequestDispatcher requestDispatcher=request.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        }
    }
    private String validateNews(News news) throws ServiceException{
        System.out.println("Я тут 6");
        String message="";

        if(news.getDescription()==null || news.getDescription().equals("")) {
            message="Field fullText is Empty";
            throw new ServiceException(message);
        }
        if(news.getTitle()==null || news.getTitle().equals("")){
            message="Field title is Empty";
            throw new ServiceException(message);
        }
        return message;
    }
}
