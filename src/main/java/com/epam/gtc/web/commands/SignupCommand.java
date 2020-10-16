package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.services.UserService;
import com.epam.gtc.services.domains.UserDomain;
import com.epam.gtc.services.domains.builders.UserDomainBuilderFromModel;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Sign up command.
 *
 * @author Fazliddin Makhsudov
 */
public class SignupCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(SignupCommand.class);
    private final UserService userService;

    public SignupCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response) {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();
        String forward = Path.PAGE_SIGNUP;
        UserModel user = (UserModel) session.getAttribute("user");
        if (user == null && Method.isPost(request)) {
            forward = handlePostRequest(request, session);
        } else if (user != null) {
            forward = Path.COMMAND_INDEX;
        }
        LOG.debug("Command finished");
        return forward;
    }

    private String handlePostRequest(HttpServletRequest request, HttpSession session) {
        String name = request.getParameter(Fields.ENTITY_NAME);
        String surname = request.getParameter(Fields.USER_SURNAME);
        String email = request.getParameter(Fields.USER_EMAIL);
        String password = request.getParameter(Fields.USER_PASSWORD);
        String forward = Path.PAGE_SIGNUP;
        List<String> errors = new ArrayList<>();
        int count = 0;
        session.removeAttribute("errorSignUp");
        LOG.trace("Set request attribute: command signup");
        request.setAttribute("command", "signup");
        LOG.trace("Request parameter: name --> " + name);
        LOG.trace("Request parameter: surname --> " + surname);
        LOG.trace("Request parameter: email --> " + email);
        LOG.trace("Request parameter: password --> " + encryptPassword(password));
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setSurname(surname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setRoleName("user");
        LOG.trace("Saving new user: " + userModel);
        try {
            userService.add(new UserDomainBuilderFromModel().create(userModel));
            UserDomain userDomain = userService.find(userModel.getEmail());
            session.setAttribute("user", new UserModelBuilder().create(userDomain));
            forward = Path.PAGE_HOME;
        } catch (AppException e) {
            LOG.error(e.getMessage());
        }
        return forward;
    }

    private String encryptPassword(final String password) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(), 0, password.length());
            return new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e.getMessage());
            return password.hashCode() + password.hashCode() + "";
        }
    }
}