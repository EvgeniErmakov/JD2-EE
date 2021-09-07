package by.newsportal.news.controller.impl;

import java.io.IOException;

import by.newsportal.news.controller.Command;
import by.newsportal.news.service.NewsService;
import by.newsportal.news.service.ServiceProvider;
import by.newsportal.news.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DeleteNews implements Command {
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
    private static final DeleteNews INSTANCE = new DeleteNews();
    public static final String ERROR_PAGE = "Controller?command=/WEB-INF/jsp/Error.jsp";

    public static DeleteNews getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String lastCommandName;
        int choosenNewsId = Integer.parseInt(request.getParameter("choosenNewsId"));
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        if (choosenNewsId < 1) {
            session.setAttribute("path", "UNKNOWN_COMMAND");
            response.sendRedirect(ERROR_PAGE);
            return;
        }

        try {
            NEWS_SERVICE.deleteNews(choosenNewsId);
            lastCommandName = "AFTER_AUTHORIZATION&currentPage=" + currentPage;
            session.setAttribute("path", lastCommandName);
            response.sendRedirect("Controller?command=" + lastCommandName);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}