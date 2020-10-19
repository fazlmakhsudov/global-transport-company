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
import java.util.*;
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
        supplyRequestWithCities(request);
        String forward;
        Object sessionUser = request.getSession().getAttribute("user");
        LOG.trace("Session user --> " + sessionUser);
        if (Objects.isNull(sessionUser)) {
            forward = Path.COMMAND_INDEX;
        } else {
            forward = handleRequest(request, sessionUser);
        }

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

        int page = optionalPage.isPresent() && Validator.isValidNumber(optionalPage.get())
                ? optionalPage.map(Integer::parseInt).orElse(1) : 1;

        int itemsPerPage = optionalItemsPerPage.isPresent() && Validator.isValidNumber(optionalItemsPerPage.get()) ?
                optionalItemsPerPage.map(Integer::parseInt).orElse(5) : 5;

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
        requestModels = sortRequestModelsList(request, requestModels);
        request.setAttribute("userRequests", requestModels);
        return forward;
    }

    private String doPost(HttpServletRequest request, RequestService requestService, int page, int itemsPerPage, UserModel sessionUser) throws ServiceException {
        String forward = String.format("%s&page=%s&itemsPerPage=%s", Path.COMMAND_USER_REQUESTS_TAB,
                page, itemsPerPage);
        StringBuilder errorRequests = new StringBuilder();
        boolean errorFlag = false;

        LOG.trace("Method is Post");
        request.getSession().removeAttribute("errorRequests");

        String action = request.getParameter(FormRequestParametersNames.ACTION);
        LOG.trace("Action --> " + action);
        if (!Validator.isValidString(action)) {
            errorFlag = true;
            errorRequests.append("Invalid action").append("<br/>");
        }

        boolean isSaveMethod = action.equalsIgnoreCase("save");

        String requestStatusName = !isSaveMethod ? "" :
                request.getParameter(FormRequestParametersNames.REQUEST_STATUS_NAME);
        LOG.trace("Request status name --> " + requestStatusName);
        if (isSaveMethod && !Validator.isValidString(requestStatusName)) {
            errorFlag = true;
            errorRequests.append("Invalid status").append("<br/>");
        }

        int userId = sessionUser.getId();
        LOG.trace("Request user id --> " + userId);

        String requestIdString = request.getParameter(FormRequestParametersNames.REQUEST_ID);
        LOG.trace("Request id --> " + requestIdString);
        if (!action.equalsIgnoreCase("add") && !Validator.isValidNumber(requestIdString)) {
            errorFlag = true;
            errorRequests.append("Invalid request id").append("<br/>");
        }

        if (errorFlag) {
            request.getSession().setAttribute("errorRequests", errorRequests.toString());
            return forward;
        }

        int requestId = action.equalsIgnoreCase("add") ? -1 :
                Integer.parseInt(requestIdString);


        switch (action) {
            case "add":
                errorFlag = false;
                errorRequests = new StringBuilder();

                RequestDomain newRequestDomain = new RequestDomain();


                String cityFromIdString = request.getParameter(FormRequestParametersNames.REQUEST_CITY_FROM_ID);
                LOG.trace("Request city from id --> " + cityFromIdString);
                if (!Validator.isValidNumber(cityFromIdString)) {
                    errorFlag = true;
                    errorRequests.append("Invalid city from id").append("<br/>");
                }

                String cityToIdString = request.getParameter(FormRequestParametersNames.REQUEST_CITY_TO_ID);
                LOG.trace("Request city to id --> " + cityToIdString);
                if (!Validator.isValidNumber(cityToIdString)) {
                    errorFlag = true;
                    errorRequests.append("Invalid city to id").append("<br/>");
                }

                String weightString = request.getParameter(FormRequestParametersNames.REQUEST_WEIGHT);
                LOG.trace("Request weight --> " + weightString);
                if (!Validator.isValidNumber(weightString)) {
                    errorFlag = true;
                    errorRequests.append("Invalid weight").append("<br/>");
                }

                String lengthString = request.getParameter(FormRequestParametersNames.REQUEST_LENGTH);
                LOG.trace("Request length --> " + lengthString);
                if (!Validator.isValidNumber(lengthString)) {
                    errorFlag = true;
                    errorRequests.append("Invalid length").append("<br/>");
                }

                String widthString = request.getParameter(FormRequestParametersNames.REQUEST_WIDTH);
                LOG.trace("Request width --> " + widthString);
                if (!Validator.isValidNumber(widthString)) {
                    errorFlag = true;
                    errorRequests.append("Invalid width").append("<br/>");
                }

                String heightString = request.getParameter(FormRequestParametersNames.REQUEST_HEIGHT);
                LOG.trace("Request height --> " + heightString);
                if (!Validator.isValidNumber(heightString)) {
                    errorFlag = true;
                    errorRequests.append("Invalid height").append("<br/>");
                }

                String contentTypeName = request.getParameter(FormRequestParametersNames.REQUEST_CONTENT_TYPE_NAME);
                LOG.trace("Request content type id --> " + contentTypeName);
                if (!Validator.isValidString(contentTypeName)) {
                    errorFlag = true;
                    errorRequests.append("Invalid content type").append("<br/>");
                }

                String deliveryDateString = request.getParameter(FormRequestParametersNames.REQUEST_DELIVERY_DATE);
                LOG.trace("Request delivery date string --> " + deliveryDateString);
                if (!Validator.isValidDate(deliveryDateString)) {
                    errorFlag = true;
                    errorRequests.append("Invalid date").append("<br/>");
                }

                LocalDateTime deliveryDate = LocalDateTime.parse(deliveryDateString);
                LOG.trace("Request delivery date --> " + deliveryDate);
                if (!Validator.isValidDateAfterToday(deliveryDate)) {
                    errorFlag = true;
                    errorRequests.append("Invalid local date").append("<br/>");
                }

                if (errorFlag) {
                    request.getSession().setAttribute("errorRequests", errorRequests.toString());
                    return forward;
                }

                int cityFromId = Integer.parseInt(cityFromIdString);
                int cityToId = Integer.parseInt(cityToIdString);
                double weight = Double.parseDouble(weightString);
                double length = Double.parseDouble(lengthString);
                double width = Double.parseDouble(widthString);
                double height = Double.parseDouble(heightString);

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
                requestDomain.setRequestStatus(RequestStatus.getEnumFromName(requestStatusName));
                boolean savedFlag = requestService.save(requestDomain);
                LOG.trace("Saved status --> " + savedFlag);
                break;

        }

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

    private List<RequestModel> sortRequestModelsList(HttpServletRequest request, List<RequestModel> requestModels) {
        final String sortparameter = request.getParameter(FormRequestParametersNames.SORT_PARAMETER);
        LOG.trace(String.format("Sort parameter --> %s", sortparameter));
        if (!Validator.isValidString(sortparameter)) {
            return requestModels;
        }
        Map<Integer, String> citiesMap = (Map<Integer, String>) request.getAttribute("citiesMap");
        switch (sortparameter) {
            case FormRequestParametersNames.REQUEST_ID:
                requestModels.sort(Comparator.comparingInt(RequestModel::getId));
                break;
            case FormRequestParametersNames.REQUEST_CITY_FROM_ID:
                requestModels.sort((r1, r2) -> {
                    String city1 = citiesMap.get(r1.getCityFromId());
                    String city2 = citiesMap.get(r2.getCityFromId());
                    return city1.compareTo(city2);
                });
                break;
            case FormRequestParametersNames.REQUEST_CITY_TO_ID:
                requestModels.sort((r1, r2) -> {
                    String city1 = citiesMap.get(r1.getCityToId());
                    String city2 = citiesMap.get(r2.getCityToId());
                    return city1.compareTo(city2);
                });
                break;
            case FormRequestParametersNames.REQUEST_DELIVERY_DATE:
                requestModels.sort(Comparator.comparing(RequestModel::getDeliveryDate));
                break;
            default:
        }
        request.setAttribute("sortparameter", sortparameter);
        LOG.trace(String.format("Sorted requestModels list --> %s", requestModels));
        return requestModels;
    }
}