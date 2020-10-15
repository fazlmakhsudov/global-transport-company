package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.Role;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.service_factory.ServiceFactory;
import com.epam.gtc.service_factory.ServiceType;
import com.epam.gtc.services.UserService;
import com.epam.gtc.services.domains.UserDomain;
import com.epam.gtc.utils.Method;
import com.epam.gtc.web.models.UserModel;
import com.epam.gtc.web.models.builders.UserModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


/**
 * Admin users page command.
 */
public class AdminUsersPageCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(AdminUsersPageCommand.class);

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws AppException {
        LOG.debug("AdminUsersPageCommand starts");
        String forward = handleRequest(request);
        LOG.debug("AdminUsersPageCommand finished");
        return forward;
    }

    private String handleRequest(final HttpServletRequest request) throws AppException {
        UserService userService = (UserService) ServiceFactory.createService(ServiceType.USER_SERVICE);
        int usersNumber = userService.countAllUsers();
        LOG.trace("Number of users : " + usersNumber);
        Optional<String> optionalPage = Optional.ofNullable(request.getParameter("page"));
        LOG.trace("optional page : " + optionalPage);
        Optional<String> optionalItemsPerPage = Optional.ofNullable(request.getParameter("itemsPerPage"));
        LOG.trace("optional items per page : " + optionalItemsPerPage);
        int page = optionalPage.map(Integer::parseInt).orElse(1);
        int itemsPerPage = optionalItemsPerPage.map(Integer::parseInt).orElse(5);
        String forward = Path.PAGE_ADMIN_USERS;
        if (Method.isPost(request)) {
            LOG.trace("Method is Post");
            String action = request.getParameter(FormRequestParameter.ACTION);
            LOG.trace("Action --> " + action);
            int userId = Integer.parseInt(request.getParameter(FormRequestParameter.USER_ID));
            if (action.equalsIgnoreCase("update")) {
                UserDomain userDomain = userService.find(userId);
                String name = request.getParameter(FormRequestParameter.USER_NAME);
                LOG.trace("User name --> " + name);
                userDomain.setName(name);
                String surname = request.getParameter(FormRequestParameter.USER_SURNAME);
                LOG.trace("User surname --> " + surname);
                String roleName = request.getParameter(FormRequestParameter.USER_ROLE_NAME);
                LOG.trace("User rolename --> " + roleName);
                userDomain.setRole(Role.getEnumFromName(roleName));
                userDomain.setSurname(surname);
                boolean updatedFlag = userService.save(userDomain);
                LOG.trace("Updated status --> " + updatedFlag);
            } else {
                boolean deletedFlag = userService.remove(userId);
                LOG.trace("Deleted status --> " + deletedFlag);
            }
            forward = String.format("%s&page=%s&itemsPerPage=%s", Path.COMMAND_ADMIN_USERS_PAGE,
                    page, itemsPerPage);
        }
        System.out.println(" --- >>> page: " + page + "  itemsPerPage: " + itemsPerPage);
        List<UserModel> userModels = new UserModelBuilder().create(userService.findAll(page, itemsPerPage));
        LOG.trace("Users : " + userModels);
        request.setAttribute("page", page);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("usersNumber", usersNumber);
        request.setAttribute("adminusers", userModels);
        return forward;
    }
}