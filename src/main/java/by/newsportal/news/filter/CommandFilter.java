package by.newsportal.news.filter;

import by.newsportal.news.controller.CommandName;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;

public class CommandFilter implements Filter {
    private static final String GO_TO_AUTHORIZATION_PAGE = "Controller?command=AUTHORIZATION_PAGE";
    private static final String GO_TO_MAIN_PAGE = "Controller?command=GO_TO_MAIN_PAGE";
    private static final String COMMAND_REQUEST_PARAM = "command";
    private static String lastCommand;
    private ServletContext context;

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
        } else if (lastCommand.equals("GO_TO_MAIN_PAGE") && !((req.getParameter(COMMAND_REQUEST_PARAM).equals("GO_TO_MAIN_PAGE"))
                || (req.getParameter(COMMAND_REQUEST_PARAM).equals("REGISTRATION_PAGE")) || (req.getParameter(COMMAND_REQUEST_PARAM).equals("CHANGE_LOCAL")))) {
            lastCommand = req.getParameter(COMMAND_REQUEST_PARAM);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(GO_TO_AUTHORIZATION_PAGE);
            requestDispatcher.forward(request, response);
            return;
        }
        lastCommand = req.getParameter(COMMAND_REQUEST_PARAM);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
