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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String lastCommandName = "GO_TO_MAIN_PAGE";
        String path;
        String title = request.getParameter("title");
        String fullText = request.getParameter("full_text");
        String brief = request.getParameter("brief");
        Integer id = Integer.parseInt(request.getParameter("choosenId"));
        String imgLink = null;

        News news = new News(id, title, fullText);

        String message = "";
        //		Part filePart = request.getPart("file");
//		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
//		InputStream fileContent = filePart.getInputStream(); //https://overcoder.net/q/1966/%D0%BA%D0%B0%D0%BA-%D0%B7%D0%B0%D0%B3%D1%80%D1%83%D0%B7%D0%B8%D1%82%D1%8C-%D1%84%D0%B0%D0%B9%D0%BB%D1%8B-%D0%BD%D0%B0-%D1%81%D0%B5%D1%80%D0%B2%D0%B5%D1%80-%D0%B8%D1%81%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D1%83%D1%8F-jsp-servlet

//		if(session==null) {
//			lastCommandName="AUTHORIZATION_PAGE";
//			request.getSession(true).setAttribute("lastURL", lastCommandName ); //for redirect in localization
//			path="Controller?commandToController=AUTHORIZATION_PAGE&message=Session is closed, please log in";
//
//			response.sendRedirect(path);
//			return;
//		}
//		User user= (User)session.getAttribute("user");
//		if(user==null) {
//
//			path="Controller?commandToController=AUTHORIZATION_PAGE&message=Session is closed, please log in";
//			lastCommandName="AUTHORIZATION_PAGE";
//			request.getSession(true).setAttribute("lastURL", lastCommandName ); //for redirect in localization
//
//			request.getSession(true).setAttribute("lastURL", path ); //for redirect in localization
//			response.sendRedirect(path);
//			return;
//		}
//		if("admin".equals(user.getRole()) || "manager".equals(user.getRole()) ) {
//			session.removeAttribute("user");
//			//log
//			lastCommandName="AUTHORIZATION_PAGE";
//			request.getSession(true).setAttribute("lastURL", lastCommandName ); //for redirect in localization
//
//			path="Controller?commandToController=AUTHORIZATION_PAGE&message=Rights of User is exceeded. Session is closed, please log in";
//			response.sendRedirect(path);
//			return;
//		} //in running
        try {
            message = validateNews(news);
            NEWS_SERVICE.update(news);
            message = "News succesfully updated";
            request.setAttribute("message", message);
            path = "GO_TO_MAIN_PAGE";
            session.setAttribute("lastURL", lastCommandName); //for redirect in localization
            response.sendRedirect("Controller?commandToController=" + path);
        } catch (ServiceException e) {
            path = "UPDATE_NEWS_PAGE&message=" + message;
            lastCommandName = "REGISTRATION_PAGE";
            session.setAttribute("lastURL", lastCommandName); //for redirect in localization
            response.sendRedirect("Controller?commandToController=" + path);
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