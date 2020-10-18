package com.epam.gtc.web.filters;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.Role;
import com.epam.gtc.web.models.UserModel;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Security filter.
 *
 * @author Fazliddin Makhsudov
 */
public class CommandAccessFilter implements Filter {

    private static final Logger LOG =
            Logger.getLogger(CommandAccessFilter.class);

    private final Map<Role, List<String>> accessMap =
            new HashMap<Role, List<String>>();
    /**
     * Accessible resources for authenticated users
     */
    private List<String> commons = new ArrayList<String>();
    /**
     * Accessible resources for everyone
     */
    private List<String> outOfControl = new ArrayList<String>();

    @Override
    public final void destroy() {
        LOG.debug("Filter destruction starts");
        // do nothing
        LOG.debug("Filter destruction finished");
    }

    @Override
    public final void doFilter(final ServletRequest request,
                               final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        LOG.debug("Filter starts");
        if (accessAllowed(request)) {
            LOG.debug("Filter finished");
            chain.doFilter(request, response);
        } else {
            String errorMessasge = "You do not have permission "
                    + "to access the requested resource";

            request.setAttribute("errorMessage", errorMessasge);
            LOG.trace("Set the request attribute: errorMessage --> "
                    + errorMessasge);

            request.getRequestDispatcher(
                    Path.PAGE_ERROR_PAGE).forward(request, response);
        }
    }


    private boolean accessAllowed(final ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getRequestURI().equalsIgnoreCase("/gtc/changeLocale")) {
            LOG.trace("Accessed allowed for changing language");
            return true;
        }
        String commandName = request.getParameter("command");
        if (commandName == null || commandName.isEmpty()) {
            return false;
        }

        if (outOfControl.contains(commandName)) {
            return true;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }

        UserModel userModel = (UserModel) session.getAttribute("user");
        if (Objects.isNull(userModel)) {
            return false;
        }
        String roleName = userModel.getRoleName();
        return accessMap.get(Role.getEnumFromName(roleName)).contains(commandName)
                || commons.contains(commandName);
    }

    @Override
    public final void init(final FilterConfig fConfig)
            throws ServletException {
        LOG.debug("Filter initialization starts");

        // roles
        accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("restricted")));
        accessMap.put(Role.MANAGER, asList(
                fConfig.getInitParameter("restricted")));
        accessMap.put(Role.USER, asList(
                fConfig.getInitParameter("common")));
        LOG.trace("Access map --> " + accessMap);

        // commons
        commons = asList(fConfig.getInitParameter("common"));
        LOG.trace("Common commands --> " + commons);

        // out of control
        outOfControl = asList(fConfig.getInitParameter("out-of-control"));
        LOG.trace("Out of control commands --> " + outOfControl);

        LOG.debug("Filter initialization finished");
    }

    private List<String> asList(final String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }

}