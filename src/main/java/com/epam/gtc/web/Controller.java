package com.epam.gtc.web;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.web.commands.Command;
import com.epam.gtc.web.commands.CommandContainer;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet controller.
 */
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 2423353715955164816L;
    private static final Logger LOG = Logger.getLogger(Controller.class);

    @Override
    protected final void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("Controller starts");

        // extract command name from the request
        String commandName = request.getParameter("command");
        LOG.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        LOG.trace("Obtained command --> " + command);

        // execute command and get forward address
        String forward = Path.PAGE_ERROR_PAGE;
        try {
            forward = command.execute(request, response);
        } catch (AppException ex) {
            request.setAttribute("errorMessage", ex.getMessage());
        }
        LOG.trace("Forward address --> " + forward);

        LOG.debug("Controller finished, now go to forward address --> " + forward);

        if (forward.contains("controller?command")) {
            response.sendRedirect(forward);
        } else {
            request.getRequestDispatcher(forward).forward(request, response);
        }
    }

    @Override
    protected final void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("Controller starts");

        // extract command name from the request
        String commandName = request.getParameter("command");
        LOG.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        LOG.trace("Obtained command --> " + command);

        // execute command and get forward address
        String forward = Path.PAGE_ERROR_PAGE;
        try {
            forward = command.execute(request, response);
        } catch (AppException ex) {
            request.setAttribute("errorMessage", ex.getMessage());
        }
        LOG.trace("Forward address --> " + forward);

        LOG.debug("Controller finished, now go to forward address --> " + forward);

        if (forward.contains("controller?command")) {
            response.sendRedirect(forward);
        } else {
            request.getRequestDispatcher(forward).forward(request, response);
        }
    }
}