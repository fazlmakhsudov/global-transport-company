package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.services.UserService;
import com.epam.gtc.services.domains.builders.UserDomainBuilderFromModel;
import com.epam.gtc.utils.Method;
import com.epam.gtc.web.models.UserModel;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


/**
 * User profile command.
 *
 * @author Fazliddin Makhsudov
 */
public class UserProfileTabCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(UserProfileTabCommand.class);
    private final UserService userService;

    public UserProfileTabCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws AppException {
        LOG.debug("UserProfileTabCommand starts");
        String forward;
        Object sessionUser = request.getSession().getAttribute("user");
        LOG.trace("Session user --> " + sessionUser);
        if (Objects.isNull(sessionUser)) {
            forward = Path.COMMAND_INDEX;
        } else {
            forward = handleRequest(request, sessionUser);
        }
        LOG.debug("UserProfileTabCommand finished");
        return forward;
    }

    private String handleRequest(final HttpServletRequest request, Object sessionUser) throws AppException {
        String forward = Path.PAGE_USER_CABINET;
        StringBuilder errorUser = new StringBuilder();
        boolean errorFlag = false;
        if (Method.isPost(request)) {
            LOG.trace("Method is Post");
            UserModel userModel = (UserModel) sessionUser;

            String name = request.getParameter(FormRequestParametersNames.USER_NAME);
            LOG.trace("User name --> " + name);
            if (!Validator.isValidString(name)) {
                errorFlag = true;
                errorUser.append("Invalid name").append("<br/>");
            }
            String surname = request.getParameter(FormRequestParametersNames.USER_SURNAME);
            LOG.trace("User surname --> " + surname);
            if (!Validator.isValidString(surname)) {
                errorFlag = true;
                errorUser.append("Invalid surname").append("<br/>");
            }

            if (errorFlag) {
                request.getSession().setAttribute("errorUser", errorUser.toString());
                return Path.COMMAND_USER_CABINET;
            }

            userModel.setName(name);
            userModel.setSurname(surname);
            String password = request.getParameter(FormRequestParametersNames.USER_PASSWORD);
            LOG.trace("User password --> " + encryptPassword(password));
            if (!Objects.isNull(password) && !password.isEmpty()) {
                userModel.setPassword(encryptPassword(password));
            }

            boolean updatedFlag = userService.save(new UserDomainBuilderFromModel().create(userModel));
            LOG.trace("Updated status --> " + updatedFlag);
            if (updatedFlag) {
                manageSessionAttributes(request, userModel);
            }
            forward = Path.COMMAND_USER_CABINET;
        }
        return forward;
    }

    private void manageSessionAttributes(HttpServletRequest request, UserModel userModel) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.setAttribute("user", userModel);

        session.removeAttribute("requeststab");
        session.removeAttribute("requeststabbody");
        session.removeAttribute("invoicestab");
        session.removeAttribute("invoicestabbody");
        session.removeAttribute("deliveriestab");
        session.removeAttribute("deliveriestabbody");

        session.setAttribute("profiletab", "active");
        session.setAttribute("profiletabbody", "show active");
    }

    private String encryptPassword(final String password) throws AppException {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(), 0, password.length());
            return new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e.getMessage());
            throw new AppException(e.getMessage(), e);
        }
    }
}