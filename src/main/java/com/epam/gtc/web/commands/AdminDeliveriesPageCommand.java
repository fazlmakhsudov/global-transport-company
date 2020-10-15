package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.services.DeliveryService;
import com.epam.gtc.services.domains.DeliveryDomain;
import com.epam.gtc.services.factory.ServiceFactory;
import com.epam.gtc.services.factory.ServiceType;
import com.epam.gtc.utils.Method;
import com.epam.gtc.web.models.DeliveryModel;
import com.epam.gtc.web.models.builders.DeliveryModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


/**
 * Admin deliveries page command.
 *
 * @author Fazliddin Makhsudov
 */
public class AdminDeliveriesPageCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(AdminDeliveriesPageCommand.class);

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws AppException {
        LOG.debug("AdminDeliveriesPageCommand starts");
        String forward = handleRequest(request);
        LOG.debug("AdminDeliveriesPageCommand finished");
        return forward;
    }

    private String handleRequest(final HttpServletRequest request) throws AppException {
        DeliveryService deliveryService = (DeliveryService) ServiceFactory.createService(ServiceType.DELIVERY_SERVICE);
        int deliveriesNumber = deliveryService.countAllDeliveries();
        LOG.trace("Number of deliveries : " + deliveriesNumber);
        Optional<String> optionalPage = Optional.ofNullable(request.getParameter("page"));
        LOG.trace("optional page : " + optionalPage);
        Optional<String> optionalItemsPerPage = Optional.ofNullable(request.getParameter("itemsPerPage"));
        LOG.trace("optional items per page : " + optionalItemsPerPage);
        int page = optionalPage.map(Integer::parseInt).orElse(1);
        int itemsPerPage = optionalItemsPerPage.map(Integer::parseInt).orElse(5);
        String forward = Path.PAGE_ADMIN_DELIVERIES;
        if (Method.isPost(request)) {
            forward = doPost(request, deliveryService, page, itemsPerPage);
        }
        List<DeliveryModel> deliveryModels = new DeliveryModelBuilder().create(deliveryService.findAll(page, itemsPerPage));
        LOG.trace("Deliveries : " + deliveryModels);
        request.setAttribute("page", page);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("deliveriesNumber", deliveriesNumber);
        request.setAttribute("adminDeliveries", deliveryModels);
        return forward;
    }

    private String doPost(HttpServletRequest request, DeliveryService deliveryService, int page, int itemsPerPage) throws com.epam.gtc.exceptions.ServiceException {
        String forward;
        LOG.trace("Method is Post");
        String action = request.getParameter(FormRequestParametersNames.ACTION);
        LOG.trace("Action --> " + action);
        int deliveryId = action.equalsIgnoreCase("add") ? -1 :
                Integer.parseInt(request.getParameter(FormRequestParametersNames.DELIVERY_ID));
        LOG.trace("Delivery id --> " + deliveryId);
        String deliveryStatusName = action.equalsIgnoreCase("remove") ? "" :
                request.getParameter(FormRequestParametersNames.DELIVERY_STATUS_NAME);
        LOG.trace("Delivery status name --> " + deliveryStatusName);

        switch (action) {
            case "add":
                DeliveryDomain newDeliveryDomain = new DeliveryDomain();
                newDeliveryDomain.setDeliveryStatus(DeliveryStatus.getEnumFromName(deliveryStatusName));
                int requestId = Integer.parseInt(request.getParameter(FormRequestParametersNames.DELIVERY_REQUEST_ID));
                LOG.trace("Delivery request id --> " + requestId);
                newDeliveryDomain.setRequestId(requestId);
                int newId = deliveryService.add(newDeliveryDomain);
                LOG.trace("Added status(new id) --> " + newId);
                break;
            case "save":
                DeliveryDomain deliveryDomain = deliveryService.find(deliveryId);
                deliveryDomain.setDeliveryStatus(DeliveryStatus.getEnumFromName(deliveryStatusName));
                boolean savedFlag = deliveryService.save(deliveryDomain);
                LOG.trace("Updated status --> " + savedFlag);
                break;
            case "remove":
                boolean removedFlag = deliveryService.remove(deliveryId);
                LOG.trace("Deleted status --> " + removedFlag);
                break;
            default:
                //TODO no action error
        }

        forward = String.format("%s&page=%s&itemsPerPage=%s", Path.COMMAND_ADMIN_DELIVERIES_PAGE,
                page, itemsPerPage);
        return forward;
    }

}