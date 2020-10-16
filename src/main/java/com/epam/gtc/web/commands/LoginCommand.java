package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.Role;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.services.UserService;
import com.epam.gtc.services.domains.UserDomain;
import com.epam.gtc.utils.Fields;
import com.epam.gtc.utils.Method;
import com.epam.gtc.web.models.UserModel;
import com.epam.gtc.web.models.builders.UserModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


/**
 * Login command.
 *
 * @author Fazliddin Makhsudov
 */
public class LoginCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        String forward = Path.PAGE_LOGIN;
        if (Method.isPost(request)) {
            forward = handlePostRequest(request);
        }
        LOG.debug("Command finished");
        return forward;
    }

    private String handlePostRequest(final HttpServletRequest request) throws AppException {
        String forward = Path.PAGE_USER_CABINET;
        HttpSession session = request.getSession();

        String email = request.getParameter(Fields.USER_EMAIL);
        LOG.trace("Request parameter: email --> " + email);
        String password = request.getParameter(Fields.USER_PASSWORD);
        session.removeAttribute("errorLogin");
        LOG.trace("Set request attribute: command login");
        request.setAttribute("command", "login");
        UserModel userModel = (UserModel) session.getAttribute("user");
        if (!Objects.isNull(userModel)) {
            LOG.debug("User is already loginned");
            request.setAttribute("errorMessage", "You have already loginned. Login out in order to login in again.");
            return Path.PAGE_ERROR_PAGE;
        }
        if (Validator.isValidEmail(email)) {
            UserDomain userDomain = userService.find(email);
            LOG.trace("Found in DB: user --> " + userDomain);
            if (userDomain != null && Validator.isValidString(password)
                    && Validator.isValidPassword(userDomain.getPassword(), encryptPassword(password))) {
                Role userRole = userDomain.getRole();
                LOG.trace("userRole --> " + userRole);

                if (userRole == Role.ADMIN) {
                    forward = Path.PAGE_ADMIN_HOME;
                }
                if (userRole == Role.MANAGER) {
                    forward = Path.PAGE_ADMIN_HOME;
                }
                if (userRole == Role.USER) {
                    forward = Path.PAGE_USER_CABINET;
                }
                userModel = new UserModelBuilder().create(userDomain);
                session.setAttribute("user", userModel);
                LOG.trace("Set the session attribute: user --> " + userModel);
            } else {
                forward = Path.PAGE_LOGIN;
                LOG.error("Cannot find user with such email/password");
                String error = "Cannot find user with such email/password";
                session.setAttribute("errorSignIn", error);
            }
        } else {
            LOG.error("Illegal email format");
            String error = "Illegal email format";
            session.setAttribute("errorSignIn", error);
            forward = Path.PAGE_ERROR_PAGE;
        }
        return forward;
    }

    private String encryptPassword(final String password) throws AppException {
        if (Objects.isNull(password) || password.isEmpty()) {
            return null;
        }
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