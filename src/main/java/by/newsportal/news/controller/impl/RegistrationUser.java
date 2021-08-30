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

public class RegistrationUser implements Command {
    private static final RegistrationUser instance = new RegistrationUser();
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final UserService USER_SERVICE = PROVIDER.getUserService();
    private static final String EMPTY_PASSWORD = "";
    public static final String SESSION_PATH = "path";
    public static final String PART_PATH = "Controller?command=";
    public static final String PATH_COMMAND_REGISTRATION = "REGISTRATION_PAGE";
    public static final String PATH_COMMAND_ERROR = "UNKNOWN_COMMAND";
    public static final String PATH_COMMAND_AUTHORIZATION = "AUTHORIZATION_PAGE";

    private RegistrationUser() {
    }

    public static RegistrationUser getInstance() {
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = PATH_COMMAND_ERROR;
        RegistrationInfo information = new RegistrationInfo(request);
        request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_REGISTRATION);
        try {
            if (EMPTY_PASSWORD.equals(information.getEnteredPassword()) || !(information.getEnteredPassword().equals(information.getRepeatedPassword()))) {
                response.sendRedirect(PART_PATH + PATH_COMMAND_REGISTRATION + "&incorrect_data_message=Incorrect data");
                return;
            }

            User user = USER_SERVICE.registration(information);

            if (user == null) {
                response.sendRedirect(PART_PATH + PATH_COMMAND_REGISTRATION + "&email_is_busy=This user is already registered");
                return;
            }
            request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_AUTHORIZATION);
            path = PATH_COMMAND_AUTHORIZATION + "&registration_message=You have been registered";
        } catch (ServiceException e) {
            e.printStackTrace();
            request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_ERROR);
        }
        response.sendRedirect("Controller?command=" + path);
    }
}