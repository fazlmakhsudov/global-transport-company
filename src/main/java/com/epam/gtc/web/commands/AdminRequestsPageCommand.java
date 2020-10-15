package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.ContentType;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.service_factory.ServiceFactory;
import com.epam.gtc.service_factory.ServiceType;
import com.epam.gtc.services.CityService;
import com.epam.gtc.services.RequestService;
import com.epam.gtc.services.domains.RequestDomain;
import com.epam.gtc.utils.Method;
import com.epam.gtc.web.models.CityModel;
import com.epam.gtc.web.models.RequestModel;
import com.epam.gtc.web.models.builders.CityModelBuilder;
import com.epam.gtc.web.models.builders.RequestModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Admin requests page command.
 */
public class AdminRequestsPageCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(AdminRequestsPageCommand.class);

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws AppException {
        LOG.debug("AdminRequestsPageCommand starts");
        String forward = handleRequest(request);
        getCities(request);
        LOG.debug("AdminRequestsPageCommand finished");
        return forward;
    }

    private String handleRequest(final HttpServletRequest request) throws AppException {
        RequestService requestService = (RequestService) ServiceFactory.createService(ServiceType.REQUEST_SERVICE);
        int requestsNumber = requestService.countAllRequests();
        LOG.trace("Number of requests : " + requestsNumber);
        Optional<String> optionalPage = Optional.ofNullable(request.getParameter("page"));
        LOG.trace("optional page : " + optionalPage);
        Optional<String> optionalItemsPerPage = Optional.ofNullable(request.getParameter("itemsPerPage"));
        LOG.trace("optional items per page : " + optionalItemsPerPage);
        int page = optionalPage.map(Integer::parseInt).orElse(1);
        int itemsPerPage = optionalItemsPerPage.map(Integer::parseInt).orElse(5);
        String forward = Path.PAGE_ADMIN_REQUESTS;
        if (Method.isPost(request)) {
            forward = doPost(request, requestService, page, itemsPerPage);
        }
        List<RequestModel> requestModels = new RequestModelBuilder().create(requestService.findAll(page, itemsPerPage));
        LOG.trace("Requests : " + requestModels);
        request.setAttribute("page", page);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("requestsNumber", requestsNumber);
        request.setAttribute("adminRequests", requestModels);
        return forward;
    }

    private String doPost(HttpServletRequest request, RequestService requestService, int page, int itemsPerPage) throws com.epam.gtc.exceptions.ServiceException {
        String forward;
        LOG.trace("Method is Post");
        String action = request.getParameter(FormRequestParameter.ACTION);
        LOG.trace("Action --> " + action);


        String deliveryDateString = action.equalsIgnoreCase("remove") ? "" :
                request.getParameter(FormRequestParameter.REQUEST_DELIVERY_DATE);
        LOG.trace("Request delivery date string --> " + deliveryDateString);
        LocalDateTime deliveryDate = action.equalsIgnoreCase("remove") ? LocalDateTime.now() :
                LocalDateTime.parse(deliveryDateString);
        LOG.trace("Request delivery date --> " + deliveryDate);

        String requestStatusName = action.equalsIgnoreCase("remove") ? "" :
                request.getParameter(FormRequestParameter.REQUEST_STATUS_NAME);
        LOG.trace("Request status name --> " + requestStatusName);


        int requestId = action.equalsIgnoreCase("add") ? -1 :
                Integer.parseInt(request.getParameter(FormRequestParameter.REQUEST_ID));
        LOG.trace("Request id --> " + requestId);


        switch (action) {
            case "add":
                RequestDomain newRequestDomain = new RequestDomain();

                int userId = Integer.parseInt(request.getParameter(FormRequestParameter.REQUEST_USER_ID));
                LOG.trace("Request user id --> " + userId);
                int cityFromId = Integer.parseInt(request.getParameter(FormRequestParameter.REQUEST_CITY_FROM_ID));
                LOG.trace("Request city from id --> " + cityFromId);
                int cityToId = Integer.parseInt(request.getParameter(FormRequestParameter.REQUEST_CITY_TO_ID));
                LOG.trace("Request city to id --> " + cityToId);
                double weight = Double.parseDouble(request.getParameter(FormRequestParameter.REQUEST_WEIGHT));
                LOG.trace("Request weight --> " + weight);
                double length = Double.parseDouble(request.getParameter(FormRequestParameter.REQUEST_LENGTH));
                LOG.trace("Request length --> " + length);
                double width = Double.parseDouble(request.getParameter(FormRequestParameter.REQUEST_WIDTH));
                LOG.trace("Request width --> " + width);
                double height = Double.parseDouble(request.getParameter(FormRequestParameter.REQUEST_HEIGHT));
                LOG.trace("Request height --> " + height);
                String contentTypeName = request.getParameter(FormRequestParameter.REQUEST_CONTENT_TYPE_NAME);
                LOG.trace("Request content type id --> " + contentTypeName);

                newRequestDomain.setCityFromId(cityFromId);
                newRequestDomain.setCityToId(cityToId);
                newRequestDomain.setWeight(weight);
                newRequestDomain.setLength(length);
                newRequestDomain.setWidth(width);
                newRequestDomain.setHeight(height);
                newRequestDomain.setDeliveryDate(deliveryDate);
                newRequestDomain.setContentType(ContentType.getEnumFromName(contentTypeName));
                newRequestDomain.setRequestStatus(RequestStatus.getEnumFromName(requestStatusName));
                newRequestDomain.setUserId(userId);

                int newId = requestService.add(newRequestDomain);
                LOG.trace("Added status(new id) --> " + newId);
                break;
            case "save":
                RequestDomain requestDomain = requestService.find(requestId);
                requestDomain.setDeliveryDate(deliveryDate);
                requestDomain.setRequestStatus(RequestStatus.getEnumFromName(requestStatusName));
                boolean savedFlag = requestService.save(requestDomain);
                LOG.trace("Saved status --> " + savedFlag);
                break;
            case "remove":
                boolean removedFlag = requestService.remove(requestId);
                LOG.trace("Removed status --> " + removedFlag);
                break;
            default:
                //TODO no action error
        }
        forward = String.format("%s&page=%s&itemsPerPage=%s", Path.COMMAND_ADMIN_REQUESTS_PAGE,
                page, itemsPerPage);
        return forward;
    }

    private void getCities(HttpServletRequest request) {
        CityService cityService = (CityService) ServiceFactory.createService( ServiceType.CITY_SERVICE);
        try {
            List<CityModel> cityModels = new CityModelBuilder().create(cityService.findAll());
            List<String> cityNames = cityModels.stream()
                    .map(cityModel -> cityModel.getName())
                    .collect(Collectors.toList());
            Map<Integer, String> citiesMap = cityModels.stream()
                    .collect(Collectors.toMap(cityModel -> cityModel.getId(),
                            cityModel -> cityModel.getName()));
            request.setAttribute("citiesNames", cityNames);
            request.setAttribute("citiesMap", citiesMap);

            LOG.trace(String.format("Cities names --> %s", cityNames));
            LOG.trace(String.format("Cities map --> %s", citiesMap));
            LOG.trace("all cities has been downloaded to context");
        } catch (AppException e) {
            LOG.trace("city downloading is failed", e);
        }
    }

}