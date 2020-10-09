package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Index command.
 */
public class IndexCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;
    private static final Logger LOG = Logger.getLogger(IndexCommand.class);

    @Override
    public final String execute(final HttpServletRequest request,
                                final HttpServletResponse response) {
        LOG.debug("Command starts");
        LOG.trace("Set request attribute: command index");
        request.setAttribute("command", "index");
        String forward = Path.PAGE_HOME;
        LOG.debug("Command finished");
        return forward;
    }
}