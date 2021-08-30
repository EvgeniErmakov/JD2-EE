package by.newsportal.news.controller.impl;

import java.io.IOException;

import by.newsportal.news.bean.RegistrationInfo;
import by.newsportal.news.bean.User;
import by.newsportal.news.controller.Command;
import by.newsportal.news.service.ServiceProvider;
import by.newsportal.news.service.UserService;
import by.newsportal.news.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationUser implements Command {
    private static final AuthorizationUser INSTANCE = new AuthorizationUser();
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final UserService USER_SERVICE = PROVIDER.getUserService();
    public static final String SESSION_PATH = "path";
    public static final String PATH_COMMAND_AUTHORIZATION = "AUTHORIZATION_PAGE";
    public static final String PATH_COMMAND_ERROR = "UNKNOWN_COMMAND";
    public static final String PATH_COMMAND_AFTER_AUTHORIZATION = "AFTER_AUTHORIZATION";
    private static final String INCORRECT_DATA = "Controller?command=AUTHORIZATION_PAGE&incorrect_data_message=Incorrect data:";
    private static final String USER_NOT_FOUND = "AUTHORIZATION_PAGE&incorrect_data_message=User is not found";

    private AuthorizationUser() {
    }

    public static AuthorizationUser getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = PATH_COMMAND_ERROR;
        RegistrationInfo info = new RegistrationInfo(request);
        request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_AUTHORIZATION);

        try {
            if (info.getEnteredPassword().equals("") || info.getEmail().equals("")) {
                response.sendRedirect(INCORRECT_DATA);
                return;
            }

            User user = USER_SERVICE.authorization(info);
            request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_AFTER_AUTHORIZATION);
            request.getSession(true).setAttribute("user", user);

            if (user == null) {
                path = USER_NOT_FOUND;
            } else {
                request.getSession(true).setAttribute("user", user);
                path = PATH_COMMAND_AFTER_AUTHORIZATION;
            }
        } catch (ServiceException e) {
            request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_ERROR);
        }
        response.sendRedirect("Controller?command=" + path);
    }
}
