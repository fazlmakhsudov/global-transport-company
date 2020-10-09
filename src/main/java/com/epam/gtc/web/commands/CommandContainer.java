package com.epam.gtc.web.commands;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holder for all commands.<br/>
 */
public final class CommandContainer {

    private static final Logger LOG = Logger.getLogger(CommandContainer.class);
    /**
     * Commands.
     */
    private static final Map<String, Command> commands =
            new TreeMap<String, Command>();

    static {
        // common commands
        commands.put("index", new IndexCommand());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("signup", new SignupCommand());
        commands.put("rates", new RatesCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("adminMainPage", new AdminMainPageCommand());
        commands.put("adminUsersPage", new AdminUsersPageCommand());

        LOG.debug("Command container was successfully initialized");
        LOG.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static Command get(final String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }

    private CommandContainer() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

}