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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationUser implements Command {
    private static final RegistrationUser instance = new RegistrationUser();
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final UserService USER_SERVICE = PROVIDER.getUserService();
    private static final String EMPTY_PASSWORD = "";
    private static final String SESSION_PATH = "path";
    private static final String PATH_COMMAND_REGISTRATION = "REGISTRATION_PAGE";
    private static final String PATH_COMMAND_ERROR = "Controller?command=UNKNOWN_COMMAND";
    private static final String PATH_COMMAND_AUTHORIZATION = "AUTHORIZATION_PAGE";
    private static final String INCORRECT_DATA = "Controller?command=REGISTRATION_PAGE&incorrect_data_message=Incorrect data";
    private static final String EMAIL_BUSY = "Controller?command=REGISTRATION_PAGE&email_is_busy=This user is already registered";
    private static final String SUCCESSFUL_REGISTRATION = "Controller?command=AUTHORIZATION_PAGE&registration_message=You have been registered";
    private static final String MESSAGE_AFTER_REGISTRATION = " has been registered.";
    private static final Logger logger = LogManager.getLogger(RegistrationUser.class);

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
                response.sendRedirect(INCORRECT_DATA);
                return;
            }

            User user = USER_SERVICE.registration(information);

            if (user == null) {
                response.sendRedirect(EMAIL_BUSY);
                return;
            }
            request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_AUTHORIZATION);
            path = SUCCESSFUL_REGISTRATION;
            logger.info(user.getEmail() + MESSAGE_AFTER_REGISTRATION);
        } catch (ServiceException e) {
            logger.error("Error in the application", e);
            request.getSession(true).setAttribute(SESSION_PATH, PATH_COMMAND_ERROR);
        }
        response.sendRedirect(path);
    }
}