package com.epam.gtc.web.listeners;

import com.epam.gtc.web.filters.EncodingFilter;
import org.apache.log4j.Logger;

import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Language Initializer.
 *
 */
public class InitializeLanguage implements ServletContextListener {
	private static final Logger LOG = Logger.getLogger(InitializeLanguage.class);
	public static final String PROPERTIES_FILE = 
			"lang";

	@Override
	public final void contextInitialized(final ServletContextEvent sce) {
		LOG.trace("Initialize language starts");
		ResourceBundle rb = ResourceBundle.getBundle(PROPERTIES_FILE);
		sce.getServletContext().setAttribute("lang", rb);
		LOG.trace("Initialize language finished");
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
	}
}