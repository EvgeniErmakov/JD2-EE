package by.newsportal.news.controller;

import java.util.HashMap;
import java.util.Map;

import by.newsportal.news.controller.impl.*;

public class CommandProvider {
    private final Map<CommandName, Command> COMMANDS = new HashMap<>();

    public CommandProvider() {
        COMMANDS.put(CommandName.GO_TO_MAIN_PAGE, GoToMainPage.getInstance());
        COMMANDS.put(CommandName.AUTHORIZATION_PAGE, GoToAuthorizationPage.getInstance());
        COMMANDS.put(CommandName.REGISTRATION_PAGE, GoToRegistrationPage.getInstance());
        COMMANDS.put(CommandName.REGISTRATION_NEW_USER, RegistrationUser.getInstance());
        COMMANDS.put(CommandName.AUTHORIZATION_USER, AuthorizationUser.getInstance());
        COMMANDS.put(CommandName.AFTER_AUTHORIZATION, AfterAuthorization.getInstance());
        COMMANDS.put(CommandName.CHANGE_LOCAL, ChangeLocal.getInstance());
        COMMANDS.put(CommandName.UPDATE_NEWS, UpdateNews.getInstance());
        COMMANDS.put(CommandName.UPDATE_NEWS_PAGE, GoToUpdateNewsPage.getInstance());
        COMMANDS.put(CommandName.DELETE_NEWS, DeleteNews.getInstance());
        COMMANDS.put(CommandName.UNKNOWN_COMMAND, UnknownCommand.getInstance());
        COMMANDS.put(CommandName.GO_TO_ADD_NEWS_PAGE, GoToAddNewsPage.getInstance());
        COMMANDS.put(CommandName.GO_TO_OFFER_NEWS_PAGE, GoToOfferNewsPage.getInstance());
        COMMANDS.put(CommandName.GO_TO_LIST_NEWS_OFFER_PAGE, GoToListNewsOfferPage.getInstance());
        COMMANDS.put(CommandName.ADD_NEWS, AddNews.getInstance());
        COMMANDS.put(CommandName.OFFER_NEWS, OfferNews.getInstance());
        COMMANDS.put(CommandName.ADD_NEWS_TO_HOME_PAGE, AddNewsToHomePage.getInstance());
    }

    public Command findCommand(String name) {
        if (name == null) {
            name = CommandName.UNKNOWN_COMMAND.toString();
        }
        CommandName commandName;
        try {
            commandName = CommandName.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandName = CommandName.UNKNOWN_COMMAND;
        }
        return COMMANDS.get(commandName);
    }
}
