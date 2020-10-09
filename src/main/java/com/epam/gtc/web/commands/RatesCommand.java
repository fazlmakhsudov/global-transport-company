package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Rates command.
 */
public class RatesCommand implements Command {

	private static final long serialVersionUID = 2735976616686657267L;
	private static final Logger LOG = Logger.getLogger(RatesCommand.class);

	@Override
	public final String execute(final HttpServletRequest request, 
			final HttpServletResponse response) {
		LOG.debug("Command starts");
		LOG.debug("Command finished");
		return Path.PAGE_RATES;
	}

}