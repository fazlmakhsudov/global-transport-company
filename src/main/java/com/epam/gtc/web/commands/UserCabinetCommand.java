package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Contact us command.
 */
public class UserCabinetCommand implements Command {

    private static final long serialVersionUID = 2735976616686657267L;
    private static final Logger LOG = Logger.getLogger(UserCabinetCommand.class);

    @Override
    public final String execute(final HttpServletRequest request,
                                final HttpServletResponse response) {
        LOG.debug("Command starts");
        HttpSession session = request.getSession();
        Object sessionUser = session.getAttribute("user");
        LOG.trace("Session user --> " + sessionUser);
        String forward;
        if (Objects.isNull(sessionUser)) {
            forward = Path.COMMAND_INDEX;
            LOG.debug(String.format("forward --> %s", Path.COMMAND_INDEX));
        } else {
            session.removeAttribute("requeststab");
            session.removeAttribute("requeststabbody");
            session.removeAttribute("invoicestab");
            session.removeAttribute("invoicestabbody");
            session.removeAttribute("deliveriestab");
            session.removeAttribute("deliveriestabbody");
            session.setAttribute("profiletab", "active");
            session.setAttribute("profiletabbody", "show active");
            forward = Path.PAGE_USER_CABINET;
            LOG.debug(String.format("forward --> %s", Path.PAGE_USER_CABINET));
        }
        LOG.debug("Command finished");
        return forward;
    }

}