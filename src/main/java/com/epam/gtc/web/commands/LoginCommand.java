package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.MySQLUserDAOImpl;
import com.epam.gtc.dao.entities.builders.UserEntityBuilder;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.services.UserService;
import com.epam.gtc.services.UserServiceImpl;
import com.epam.gtc.services.domains.UserDomain;
import com.epam.gtc.services.domains.builders.UserDomainBuilderFromEntity;
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
        String forward;
        HttpSession session = request.getSession();
        UserService userService = new UserServiceImpl(new MySQLUserDAOImpl(), new UserEntityBuilder(),
                new UserDomainBuilderFromEntity());
        String email = request.getParameter(Fields.USER_EMAIL);
        LOG.trace("Request parameter: email --> " + email);
        String password = request.getParameter(Fields.USER_PASSWORD);
        session.removeAttribute("errorSignIn");
        LOG.trace("Set request attribute: command login");
        request.setAttribute("command", "login");
        UserModel userModel = (UserModel) session.getAttribute("user");
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            if (userModel == null) {
                forward = Path.PAGE_LOGIN;
            } else {
                forward = Path.COMMAND_INDEX;
            }
        } else {
            UserDomain foundUser;
            try {
                foundUser = userService.find(email);
            } catch (AppException e) {
                LOG.error(Messages.ERR_CANNOT_HANDLE_POST_REQUEST);
                throw new AppException(Messages.ERR_CANNOT_HANDLE_POST_REQUEST, e);
            }
            if (!Objects.isNull(foundUser) && foundUser.getPassword().equals(encryptPassword(password))) {
                forward = Path.COMMAND_USER_CABINET;
                session.setAttribute("user", new UserModelBuilder().create(foundUser));
                System.out.println("password is correct");
            } else {
                System.out.println("password is incorrect");
                forward = Path.PAGE_ERROR_PAGE;
            }
            /**
             * For analyzing
             * 				if (Validator.isValidEmail(email)) {
             * 					user = manager.findUserByEmail(email);
             * 					LOG.trace("Found in DB: user --> " + user);
             * 					if (user != null && Validator.isValidPassword(user.getPassword(), password)) {
             * 						Role userRole = Role.getRole(user);
             * 						LOG.trace("userRole --> " + userRole);
             *
             * 						if (userRole == Role.ADMIN) {
             * 							forward = Path.COMMAND_ADMIN;
             *                                                }
             * 						if (userRole == Role.MANAGER) {
             * 							forward = Path.COMMAND_MANAGER;
             *                        }
             * 						if (userRole == Role.CLIENT) {
             * 							forward = Path.COMMAND_CLIENT;
             *                        }
             * 						session.setAttribute("cabinet", forward);
             * 						session.setAttribute("user", user);
             * 						LOG.trace("Set the session attribute: user --> " + user);
             *
             * 						session.setAttribute("userRole", userRole);
             * 						LOG.trace("Set the session attribute: userRole --> " + userRole);
             *
             * 						LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());
             **                    } else {
             * 						forward = Path.PAGE_LOGIN;
             * 						LOG.error("Cannot find user with such email/password");
             * 						error = "Cannot find user with such email/password";
             * 						session.setAttribute("errorSignIn", error);
             *                    }
             *                }                 else {
             * 					forward = Path.PAGE_LOGIN;
             * 					LOG.error("Illegal email format");
             * 					error = "Illegal email format";
             * 					session.setAttribute("errorSignIn", error);
             *                                }
             */
        }
        return forward;
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