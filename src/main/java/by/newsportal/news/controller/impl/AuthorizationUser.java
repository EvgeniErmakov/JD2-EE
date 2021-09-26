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


public class AuthorizationUser implements Command {
    private static final AuthorizationUser INSTANCE = new AuthorizationUser();
    private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
    private static final UserService USER_SERVICE = PROVIDER.getUserService();
    private static final String SESSION_PATH = "path";
    private static final String ATTRIBUTE_USER = "user";
    private static final String PATH_COMMAND_ERROR = "Controller?command=UNKNOWN_COMMAND";
    private static final String PATH_COMMAND_AFTER_AUTHORIZATION = "Controller?command=AFTER_AUTHORIZATION";
    private static final String INCORRECT_DATA = "Controller?command=AUTHORIZATION_PAGE&incorrect_data_message=Incorrect data:";
    private static final String USER_NOT_FOUND = "Controller?command=AUTHORIZATION_PAGE&incorrect_data_message=User is not found";
    private static final String AFTER_AUTHORIZATION = "AFTER_AUTHORIZATION";
    private static final String MESSAGE_AFTER_AUTHORIZATION = " has been authorized.";
    private static final Logger logger = LogManager.getLogger(AuthorizationUser.class);

    private AuthorizationUser() {
    }

    public static AuthorizationUser getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RegistrationInfo info = new RegistrationInfo(request);

        try {
            if (info.getEnteredPassword().equals("") || info.getEmail().equals("")) {
                response.sendRedirect(INCORRECT_DATA);
                return;
            }

            User user = USER_SERVICE.authorization(info);
            request.getSession(true).setAttribute(SESSION_PATH, AFTER_AUTHORIZATION);

            if (user == null) {
                response.sendRedirect(USER_NOT_FOUND);
            } else {
                request.getSession().setAttribute(ATTRIBUTE_USER, user);
                logger.info(user.getName() + MESSAGE_AFTER_AUTHORIZATION);
                response.sendRedirect(PATH_COMMAND_AFTER_AUTHORIZATION);
            }

        } catch (ServiceException e) {
            logger.error("Error in the application", e);
            response.sendRedirect(PATH_COMMAND_ERROR);
        }

    }
}
