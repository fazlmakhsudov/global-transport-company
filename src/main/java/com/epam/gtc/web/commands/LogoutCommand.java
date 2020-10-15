package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Logout command.
 */
public class LogoutCommand implements Command {

    private static final long serialVersionUID = -2785976616686657267L;
    private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

    @Override
    public final String execute(final HttpServletRequest request,
                                final HttpServletResponse response) {
        LOG.debug("Command starts");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        LOG.debug("Command finished");
        return Path.PAGE_LOGIN;
    }

}