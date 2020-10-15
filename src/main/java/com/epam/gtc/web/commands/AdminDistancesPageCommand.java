package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.CommandException;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.CityService;
import com.epam.gtc.services.DistanceService;
import com.epam.gtc.services.domains.DistanceDomain;
import com.epam.gtc.services.factory.ServiceFactory;
import com.epam.gtc.services.factory.ServiceType;
import com.epam.gtc.utils.Method;
import com.epam.gtc.web.models.CityModel;
import com.epam.gtc.web.models.DistanceModel;
import com.epam.gtc.web.models.builders.CityModelBuilder;
import com.epam.gtc.web.models.builders.DistanceModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Admin distance page command.
 *
 * @author Fazliddin Makhsudov
 */
public class AdminDistancesPageCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(AdminDistancesPageCommand.class);

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws AppException {
        LOG.debug("AdminDistancesPageCommand starts");
        String forward = handleRequest(request);
        getCities(request);
        LOG.debug("AdminDistancesPageCommand finished");
        return forward;
    }

    private String handleRequest(final HttpServletRequest request) throws AppException {
        DistanceService distanceService = (DistanceService) ServiceFactory.createService(ServiceType.DISTANCE_SERVICE);
        int distancesNumber = distanceService.countAllDistances();
        LOG.trace("Number of distances : " + distancesNumber);
        Optional<String> optionalPage = Optional.ofNullable(request.getParameter("page"));
        LOG.trace("optional page : " + optionalPage);
        Optional<String> optionalItemsPerPage = Optional.ofNullable(request.getParameter("itemsPerPage"));
        LOG.trace("optional items per page : " + optionalItemsPerPage);
        int page = optionalPage.map(Integer::parseInt).orElse(1);
        int itemsPerPage = optionalItemsPerPage.map(Integer::parseInt).orElse(5);
        String forward = Path.PAGE_ADMIN_DISTANCES;
        if (Method.isPost(request)) {
            forward = doPost(request, distanceService, page, itemsPerPage);
        }
        List<DistanceModel> distanceModels = new DistanceModelBuilder().create(distanceService.findAll(page, itemsPerPage));
        LOG.trace("Distances : " + distanceModels);
        request.setAttribute("page", page);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("distancesNumber", distancesNumber);
        request.setAttribute("adminDistances", distanceModels);
        return forward;
    }

    private String doPost(HttpServletRequest request, DistanceService distanceService, int page, int itemsPerPage) throws ServiceException {
        String forward;
        LOG.trace("Method is Post");
        String action = request.getParameter(FormRequestParametersNames.ACTION);
        LOG.trace("Action --> " + action);

        int fromCityId = action.equalsIgnoreCase("remove") ? -1 :
                Integer.parseInt(request.getParameter(FormRequestParametersNames.DISTANCE_FROM_CITY_ID));
        LOG.trace("Distance from city id --> " + fromCityId);
        int toCityId = action.equalsIgnoreCase("remove") ? -1 :
                Integer.parseInt(request.getParameter(FormRequestParametersNames.DISTANCE_TO_CITY_ID));
        LOG.trace("Distance to city id --> " + toCityId);
        double distance = action.equalsIgnoreCase("remove") ? -1d :
                Double.parseDouble(request.getParameter(FormRequestParametersNames.DISTANCE_DISTANCE));
        LOG.trace("Distance between cities --> " + distance);

        int distanceId = action.equalsIgnoreCase("add") ? -1 :
                Integer.parseInt(request.getParameter(FormRequestParametersNames.DISTANCE_ID));
        LOG.trace("Distance id --> " + distanceId);
        switch (action) {
            case "add":
                DistanceDomain newDistanceDomain = new DistanceDomain();
                newDistanceDomain.setFromCityId(fromCityId);
                newDistanceDomain.setToCityId(toCityId);
                newDistanceDomain.setDistance(distance);
                int newId = distanceService.add(newDistanceDomain);
                LOG.trace("Added status(new id) --> " + newId);
                break;
            case "save":
                DistanceDomain distanceDomain = distanceService.find(distanceId);
                distanceDomain.setFromCityId(fromCityId);
                distanceDomain.setToCityId(toCityId);
                distanceDomain.setDistance(distance);
                boolean savedFlag = distanceService.save(distanceDomain);
                LOG.trace("Saved status --> " + savedFlag);
                break;
            case "remove":
                boolean removedFlag = distanceService.remove(distanceId);
                LOG.trace("Removed status --> " + removedFlag);
                break;
            default:
                //TODO no action error
        }

        forward = String.format("%s&page=%s&itemsPerPage=%s", Path.COMMAND_ADMIN_DISTANCES_PAGE,
                page, itemsPerPage);
        return forward;
    }

    private void getCities(HttpServletRequest request) {
        CityService cityService = (CityService) ServiceFactory.createService(ServiceType.CITY_SERVICE);
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
            throw new CommandException(e.getMessage(), e);
        }
    }

}