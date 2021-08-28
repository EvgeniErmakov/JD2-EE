package by.newsportal.news.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String COMMAND_REQUEST_PARAM = "command";

	private final CommandProvider provider = new CommandProvider();
	
    public Controller() {
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String commandName = request.getParameter(COMMAND_REQUEST_PARAM);	
		Command command = provider.findCommand(commandName);
		command.execute(request, response);
	}
}