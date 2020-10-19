package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.CommandException;
import com.epam.gtc.services.CityService;
import com.epam.gtc.services.DeliveryService;
import com.epam.gtc.services.DistanceService;
import com.epam.gtc.services.RateService;
import com.epam.gtc.services.domains.DeliveryDomain;
import com.epam.gtc.services.domains.DistanceDomain;
import com.epam.gtc.utils.Method;
import com.epam.gtc.web.models.CityModel;
import com.epam.gtc.web.models.DeliveryModel;
import com.epam.gtc.web.models.DistanceModel;
import com.epam.gtc.web.models.RateModel;
import com.epam.gtc.web.models.builders.CityModelBuilder;
import com.epam.gtc.web.models.builders.DeliveryModelBuilder;
import com.epam.gtc.web.models.builders.DistanceModelBuilder;
import com.epam.gtc.web.models.builders.RateModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Delivery map page command.
 *
 * @author Fazliddin Makhsudov
 */
public class DeliveryMapPageCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(DeliveryMapPageCommand.class);
    private final DistanceService distanceService;
    private final CityService cityService;
    private final RateService rateService;


    public DeliveryMapPageCommand(DistanceService distanceService, CityService cityService, RateService rateService) {
        this.distanceService = distanceService;
        this.cityService = cityService;
        this.rateService = rateService;
    }

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws AppException {
        if (Method.isGet(request)) {
            LOG.debug("DeliveryMapPageCommand starts");
            supplyRequestWithCities(request);
            String forward = handleRequest(request);
            LOG.debug("DeliveryMapPageCommand finished");
            return forward;
        }
        return Path.COMMAND_INDEX;

    }

    private String handleRequest(final HttpServletRequest request) throws AppException {
        Map<Integer, String> citiesMap = (Map<Integer, String>) request.getAttribute("citiesMap");

        List<String> sortedCitiesNames = citiesMap.values().stream().collect(Collectors.toList());
        sortedCitiesNames.sort(String::compareTo);

        Map<String, Integer> cities = citiesMap.keySet().stream()
                .collect(Collectors.toMap(cityId -> citiesMap.get(cityId), cityId -> cityId));

        Map<String, List<RateModel>> deliveryMapRates = new HashMap<>();
        Map<String, List<DistanceModel>> deliveryMapDestinations = new HashMap<>();

        DistanceModelBuilder distanceModelBuilder = new DistanceModelBuilder();
        RateModelBuilder rateModelBuilder = new RateModelBuilder();

        for (String cityName : sortedCitiesNames) {
            int cityId = cities.get(cityName);

            List<DistanceModel> distanceModels = distanceModelBuilder.create(distanceService.findAll(cityId));
            deliveryMapDestinations.put(cityName, distanceModels);
            double maxDistance = distanceModels.stream().map(DistanceModel::getDistance).max(Double::compareTo).get();
            List<RateModel> rateModels = rateModelBuilder.create( rateService.findAll(maxDistance));
            deliveryMapRates.put(cityName, rateModels);
        }
        LOG.trace(String.format("Delivery map rates -> %s", deliveryMapRates));
        LOG.trace(String.format("Delivery map destinations -> %s", deliveryMapDestinations));
        request.setAttribute("deliveryMapRates", deliveryMapRates);
        request.setAttribute("deliveryMapDestinations", deliveryMapDestinations);
        return Path.PAGE_DELIVERY_MAP;
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
            LOG.trace("all cities has been downloaded");
        } catch (AppException e) {
            LOG.trace("city downloading is failed", e);
            throw new CommandException(e.getMessage(), e);
        }
    }


}