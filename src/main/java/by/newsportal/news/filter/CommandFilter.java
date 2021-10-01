package by.newsportal.news.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class CommandFilter implements Filter {
    private static final String GO_TO_AUTHORIZATION_PAGE = "Controller?command=AUTHORIZATION_PAGE";
    private static final String GO_TO_MAIN_PAGE = "Controller?command=GO_TO_MAIN_PAGE";
    private static final String COMMAND_GO_TO_MAIN_PAGE = "GO_TO_MAIN_PAGE";
    private static final String COMMAND_REGISTRATION_PAGE = "REGISTRATION_PAGE";
    private static final String COMMAND_CHANGE_LOCAL = "CHANGE_LOCAL";
    private static final String COMMAND_REQUEST_PARAM = "command";
    private static String lastCommand;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(true);

        if (session.getAttributeNames().hasMoreElements() == false) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(GO_TO_MAIN_PAGE);
            requestDispatcher.forward(request, response);
        } else if (lastCommand.equals("GO_TO_MAIN_PAGE") && !checkLastCommand(req.getParameter(COMMAND_REQUEST_PARAM))) {
            lastCommand = req.getParameter(COMMAND_REQUEST_PARAM);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(GO_TO_AUTHORIZATION_PAGE);
            requestDispatcher.forward(request, response);
            return;
        }

        lastCommand = req.getParameter(COMMAND_REQUEST_PARAM);
        chain.doFilter(request, response);
    }

    public boolean checkLastCommand(String command) {
        switch (command) {
            case (COMMAND_GO_TO_MAIN_PAGE):
                return true;
            case (COMMAND_REGISTRATION_PAGE):
                return true;
            case (COMMAND_CHANGE_LOCAL):
                return true;
            default:
                return false;
        }
    }

    @Override
    public void destroy() {
    }
}
