package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.DeliveryService;
import com.epam.gtc.services.domains.DeliveryDomain;
import com.epam.gtc.services.factory.ServiceFactory;
import com.epam.gtc.services.factory.ServiceType;
import com.epam.gtc.utils.Method;
import com.epam.gtc.web.models.DeliveryModel;
import com.epam.gtc.web.models.UserModel;
import com.epam.gtc.web.models.builders.DeliveryModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * User Deliveries tab command.
 *
 * @author Fazliddin Makhsudov
 */
public class UserDeliveriesTabCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(UserDeliveriesTabCommand.class);

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws AppException {
        LOG.debug("UserDeliveriesTabCommand starts");
        String forward;
        Object sessionUser = request.getSession().getAttribute("user");
        LOG.trace("Session user --> " + sessionUser);
        if (Objects.isNull(sessionUser)) {
            forward = Path.COMMAND_INDEX;
        } else {
            forward = handleRequest(request, sessionUser);
        }
        LOG.debug("UserDeliveriesTabCommand finished");
        return forward;
    }

    private String handleRequest(final HttpServletRequest request, Object sessionUser) throws AppException {
        DeliveryService deliveryService = (DeliveryService) ServiceFactory.createService(ServiceType.DELIVERY_SERVICE);
        UserModel userModel = (UserModel) sessionUser;
        int deliveriesNumber = deliveryService.countUserDeliveries(userModel.getId());
        LOG.trace("Number of user deliveries : " + deliveriesNumber);
        Optional<String> optionalPage = Optional.ofNullable(request.getParameter("page"));
        LOG.trace("optional page : " + optionalPage);
        Optional<String> optionalItemsPerPage = Optional.ofNullable(request.getParameter("itemsPerPage"));
        LOG.trace("optional items per page : " + optionalItemsPerPage);
        int page = optionalPage.map(Integer::parseInt).orElse(1);
        int itemsPerPage = optionalItemsPerPage.map(Integer::parseInt).orElse(5);
        String forward = Path.PAGE_USER_CABINET;
        if (Method.isPost(request)) {
            forward = doPost(request, deliveryService, page, itemsPerPage);
        }
        List<DeliveryModel> deliveryModels = new DeliveryModelBuilder().create(deliveryService.findAll(page, itemsPerPage, userModel.getId()));
        LOG.trace("Deliverys : " + deliveryModels);
        manageSessionAttributes(request.getSession());
        request.setAttribute("page", page);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("deliveriesNumber", deliveriesNumber);
        request.setAttribute("userDeliveries", deliveryModels);
        return forward;
    }

    private String doPost(HttpServletRequest request, DeliveryService deliveryService, int page, int itemsPerPage) throws ServiceException {
        String forward;
        LOG.trace("Method is Post");
        String action = request.getParameter(FormRequestParametersNames.ACTION);
        LOG.trace("Action --> " + action);

        switch (action) {
            case "save":
                String deliveryStatusName = request.getParameter(FormRequestParametersNames.DELIVERY_STATUS_NAME);
                LOG.trace("Delivery status name --> " + deliveryStatusName);
                int deliveryId = Integer.parseInt(request.getParameter(FormRequestParametersNames.DELIVERY_ID));
                LOG.trace("Delivery id --> " + deliveryId);
                DeliveryDomain deliveryDomain = deliveryService.find(deliveryId);
                deliveryDomain.setDeliveryStatus(DeliveryStatus.getEnumFromName(deliveryStatusName));

                boolean savedFlag = deliveryService.save(deliveryDomain);
                LOG.trace("Saved status --> " + savedFlag);
                break;
            default:
                //TODO no action error
        }

        forward = String.format("%s&page=%s&itemsPerPage=%s", Path.COMMAND_USER_DELIVERIES_TAB,
                page, itemsPerPage);
        return forward;
    }

    private void manageSessionAttributes(HttpSession session) {
        session.removeAttribute("profiletab");
        session.removeAttribute("profiletabbody");
        session.removeAttribute("requeststab");
        session.removeAttribute("requeststabbody");
        session.removeAttribute("invoicestab");
        session.removeAttribute("invoicestabbody");

        session.setAttribute("deliveriestab", "active");
        session.setAttribute("deliveriestabbody", "show active");
    }
}