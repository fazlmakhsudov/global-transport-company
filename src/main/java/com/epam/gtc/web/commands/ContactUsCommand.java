package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Contact us command.
 */
public class ContactUsCommand implements Command {

    private static final long serialVersionUID = 2735976616686657267L;
    private static final Logger LOG = Logger.getLogger(ContactUsCommand.class);

    @Override
    public final String execute(final HttpServletRequest request,
                                final HttpServletResponse response) {
        LOG.debug("Command starts");
        LOG.debug(String.format("forward --> %s", Path.PAGE_CONTACT_US));
        LOG.debug("Command finished");
        return Path.PAGE_CONTACT_US;
    }

}