package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.ContentType;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.CommandException;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.CityService;
import com.epam.gtc.services.DistanceService;
import com.epam.gtc.services.RequestService;
import com.epam.gtc.services.domains.RequestDomain;
import com.epam.gtc.utils.Method;
import com.epam.gtc.web.models.CityModel;
import com.epam.gtc.web.models.DistanceModel;
import com.epam.gtc.web.models.RequestModel;
import com.epam.gtc.web.models.UserModel;
import com.epam.gtc.web.models.builders.CityModelBuilder;
import com.epam.gtc.web.models.builders.DistanceModelBuilder;
import com.epam.gtc.web.models.builders.RequestModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * User requests tab command.
 *
 * @author Fazliddin Makhsudov
 */
public class UserRequestsTabCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(UserRequestsTabCommand.class);
    private final RequestService requestService;
    private final CityService cityService;
    private final DistanceService distanceService;

    public UserRequestsTabCommand(RequestService requestService, CityService cityService, DistanceService distanceService) {
        this.requestService = requestService;
        this.cityService = cityService;
        this.distanceService = distanceService;
    }

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws AppException {
        LOG.debug("UserRequestsTabCommand starts");
        String forward;
        Object sessionUser = request.getSession().getAttribute("user");
        LOG.trace("Session user --> " + sessionUser);
        if (Objects.isNull(sessionUser)) {
            forward = Path.COMMAND_INDEX;
        } else {
            forward = handleRequest(request, sessionUser);
        }
        supplyRequestWithCities(request);
        LOG.debug("UserRequestsTabCommand finished");
        return forward;
    }

    private String handleRequest(final HttpServletRequest request, Object sessionUser) throws AppException {

        UserModel userModel = (UserModel) sessionUser;
        int requestsNumber = requestService.countUserRequests(userModel.getId());
        LOG.trace("Number of user requests : " + requestsNumber);
        Optional<String> optionalPage = Optional.ofNullable(request.getParameter("page"));
        LOG.trace("optional page : " + optionalPage);
        Optional<String> optionalItemsPerPage = Optional.ofNullable(request.getParameter("itemsPerPage"));
        LOG.trace("optional items per page : " + optionalItemsPerPage);
        int page = optionalPage.map(Integer::parseInt).orElse(1);
        int itemsPerPage = optionalItemsPerPage.map(Integer::parseInt).orElse(5);
        String forward = Path.PAGE_USER_CABINET;
        if (Method.isPost(request)) {
            forward = doPost(request, requestService, page, itemsPerPage, userModel);
        }
        List<RequestModel> requestModels = new RequestModelBuilder().create(requestService.findAll(page, itemsPerPage, userModel.getId()));
        LOG.trace("Requests : " + requestModels);
        manageSessionAttributes(request.getSession());
        request.setAttribute("page", page);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("requestsNumber", requestsNumber);
        request.setAttribute("userRequests", requestModels);
        return forward;
    }

    private String doPost(HttpServletRequest request, RequestService requestService, int page, int itemsPerPage, UserModel sessionUser) throws ServiceException {
        String forward;
        LOG.trace("Method is Post");
        String action = request.getParameter(FormRequestParametersNames.ACTION);
        LOG.trace("Action --> " + action);
        boolean isAddSaveAction = action.equalsIgnoreCase("add") || action.equalsIgnoreCase("save");

        int userId = sessionUser.getId();
        LOG.trace("Request user id --> " + userId);
        String contentTypeName = !isAddSaveAction ? "" : request.getParameter(FormRequestParametersNames.REQUEST_CONTENT_TYPE_NAME);
        LOG.trace("Request content type id --> " + contentTypeName);

        int requestId = action.equalsIgnoreCase("add") ? -1 :
                Integer.parseInt(request.getParameter(FormRequestParametersNames.REQUEST_ID));
        LOG.trace("Request id --> " + requestId);


        switch (action) {
            case "add":
                int cityFromId = Integer.parseInt(request.getParameter(FormRequestParametersNames.REQUEST_CITY_FROM_ID));
                LOG.trace("Request city from id --> " + cityFromId);
                int cityToId = Integer.parseInt(request.getParameter(FormRequestParametersNames.REQUEST_CITY_TO_ID));
                LOG.trace("Request city to id --> " + cityToId);
                double weight = Double.parseDouble(request.getParameter(FormRequestParametersNames.REQUEST_WEIGHT));
                LOG.trace("Request weight --> " + weight);
                double length = Double.parseDouble(request.getParameter(FormRequestParametersNames.REQUEST_LENGTH));
                LOG.trace("Request length --> " + length);
                double width = Double.parseDouble(request.getParameter(FormRequestParametersNames.REQUEST_WIDTH));
                LOG.trace("Request width --> " + width);
                double height = Double.parseDouble(request.getParameter(FormRequestParametersNames.REQUEST_HEIGHT));
                LOG.trace("Request height --> " + height);

                String deliveryDateString = request.getParameter(FormRequestParametersNames.REQUEST_DELIVERY_DATE);
                LOG.trace("Request delivery date string --> " + deliveryDateString);
                LocalDateTime deliveryDate = Objects.isNull(deliveryDateString) || deliveryDateString.isEmpty() ?
                        LocalDateTime.now() : LocalDateTime.parse(deliveryDateString);
                LOG.trace("Request delivery date --> " + deliveryDate);
                RequestDomain newRequestDomain = new RequestDomain();
                newRequestDomain.setCityFromId(cityFromId);
                newRequestDomain.setCityToId(cityToId);
                newRequestDomain.setWeight(weight);
                newRequestDomain.setLength(length);
                newRequestDomain.setWidth(width);
                newRequestDomain.setHeight(height);
                newRequestDomain.setDeliveryDate(deliveryDate);
                newRequestDomain.setContentType(ContentType.getEnumFromName(contentTypeName));
                newRequestDomain.setRequestStatus(RequestStatus.WAITING_FOR_MANAGER_REVIEW);
                newRequestDomain.setUserId(userId);
                int newId = requestService.add(newRequestDomain);
                LOG.trace("Added status(new id) --> " + newId);
                break;
            case "save":
                RequestDomain requestDomain = requestService.find(requestId);
                requestDomain.setContentType(ContentType.getEnumFromName(contentTypeName));
                String requestStatusName = request.getParameter(FormRequestParametersNames.REQUEST_STATUS_NAME);
                LOG.trace("Request status name --> " + requestStatusName);
                requestDomain.setRequestStatus(RequestStatus.getEnumFromName(requestStatusName));
                boolean savedFlag = requestService.save(requestDomain);
                LOG.trace("Saved status --> " + savedFlag);
                break;
            default:
                //TODO no action error
        }
        forward = String.format("%s&page=%s&itemsPerPage=%s", Path.COMMAND_USER_REQUESTS_TAB,
                page, itemsPerPage);
        return forward;
    }

    private void manageSessionAttributes(HttpSession session) {
        session.removeAttribute("profiletab");
        session.removeAttribute("profiletabbody");
        session.removeAttribute("invoicestab");
        session.removeAttribute("invoicestabbody");
        session.removeAttribute("deliveriestab");
        session.removeAttribute("deliveriestabbody");

        session.setAttribute("requeststab", "active");
        session.setAttribute("requeststabbody", "show active");
    }

    private void supplyRequestWithCities(HttpServletRequest request) {

        try {
            List<CityModel> cityModels = new CityModelBuilder().create(cityService.findAll());
            List<DistanceModel> distanceModels = new DistanceModelBuilder().create(distanceService.findAll());
            List<Integer> distanceIdFilterList = distanceModels.stream().map(DistanceModel::getFromCityId).collect(Collectors.toList());
            List<String> cityNames = cityModels.stream()
                    .map(CityModel::getName)
                    .collect(Collectors.toList());
            Map<Integer, String> citiesMap = cityModels.stream()
                    .filter(cityModel -> distanceIdFilterList.contains(cityModel.getId()))
                    .collect(Collectors.toMap(CityModel::getId,
                            CityModel::getName));
            request.setAttribute("citiesNames", cityNames);
            request.setAttribute("citiesMap", citiesMap);

            LOG.trace(String.format("Cities names --> %s", cityNames));
            LOG.trace(String.format("Cities map --> %s", citiesMap));
            LOG.trace("all cities has been downloaded to context");
        } catch (AppException e) {
            LOG.trace("city downloading is failed", e);
            throw new CommandException(e.getMessage(), e);
        }
    }
}