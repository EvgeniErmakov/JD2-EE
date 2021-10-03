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
    private static final String ATTRIBUTE_USER = "user";
    private static final String GO_TO_ERROR_PAGE = "Controller?command=UNKNOWN_COMMAND";
    private static final String PATH_COMMAND_AFTER_AUTHORIZATION = "Controller?command=GO_TO_AFTER_AUTHORIZATION_PAGE";
    private static final String INCORRECT_DATA_MESSAGE = "Controller?command=AUTHORIZATION_PAGE&incorrect_data_message=Incorrect data:";
    private static final String USER_NOT_FOUND_MESSAGE = "Controller?command=AUTHORIZATION_PAGE&incorrect_data_message=User is not found";
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
                response.sendRedirect(INCORRECT_DATA_MESSAGE);
                return;
            }

            User user = USER_SERVICE.authorization(info);

            if (user == null) {
                response.sendRedirect(USER_NOT_FOUND_MESSAGE);
            } else {
                request.getSession().setAttribute(ATTRIBUTE_USER, user);
                logger.info(user.getName() + MESSAGE_AFTER_AUTHORIZATION);
                response.sendRedirect(PATH_COMMAND_AFTER_AUTHORIZATION);
            }

        } catch (ServiceException e) {
            logger.error("Error in the application", e);
            response.sendRedirect(GO_TO_ERROR_PAGE);
        }
    }
}
