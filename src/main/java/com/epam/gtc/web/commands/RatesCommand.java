package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.CommandException;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.CityService;
import com.epam.gtc.services.DistanceService;
import com.epam.gtc.services.RateService;
import com.epam.gtc.services.factory.ServiceFactory;
import com.epam.gtc.services.factory.ServiceType;
import com.epam.gtc.utils.Method;
import com.epam.gtc.web.models.CityModel;
import com.epam.gtc.web.models.DistanceModel;
import com.epam.gtc.web.models.RateModel;
import com.epam.gtc.web.models.builders.CityModelBuilder;
import com.epam.gtc.web.models.builders.DistanceModelBuilder;
import com.epam.gtc.web.models.builders.RateModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Rates command.
 *
 * @author Fazliddin Makhsudov
 */
public class RatesCommand implements Command {

    private static final long serialVersionUID = 2735976616686657267L;
    private static final Logger LOG = Logger.getLogger(RatesCommand.class);
    private final CityService cityService;
    private final DistanceService distanceService;
    private final RateService rateService;

    public RatesCommand(CityService cityService, DistanceService distanceService, RateService rateService) {
        this.cityService = cityService;
        this.distanceService = distanceService;
        this.rateService = rateService;
    }

    @Override
    public final String execute(final HttpServletRequest request,
                                final HttpServletResponse response) {
        if (Method.isGet(request)) {
            LOG.debug("Command starts");

            String rateName = request.getParameter(FormRequestParametersNames.RATE_NAME);
            boolean rateNameFlag = !Objects.isNull(rateName) && !rateName.isEmpty();
            if (rateNameFlag) {
                handleRequest(request, rateName);
            } else {
                handleRequest(request);
            }
            LOG.debug(String.format("forward --> %s", Path.PAGE_RATES));
            LOG.debug("Command finished");
            return Path.PAGE_RATES;
        }
        return Path.COMMAND_INDEX;
    }

    private void handleRequest(HttpServletRequest request, String rateName) {

        List<RateModel> rates;
        List<DistanceModel> distances;
        List<CityModel> cityModels;
        RateModel rateModel;
        try {
            RateModelBuilder rateModelBuilder = new RateModelBuilder();
            rateModel = rateModelBuilder.create(rateService.find(rateName));
            distances = new DistanceModelBuilder().create(rateModel.getMaxDistance() <= 0d ?
                    distanceService.findAll() : distanceService.findAll(rateModel.getMaxDistance()));
            rates = rateModelBuilder.create(rateService.findAll());
            cityModels = new CityModelBuilder().create(cityService.findAll());
        } catch (BuilderException | ServiceException e) {
            LOG.error(e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        Map<Integer, DistanceModel> distancesMap = distances.stream()
                .collect(Collectors.toMap(DistanceModel::getId, distance -> distance));
        Map<Integer, String> citiesMap = cityModels.stream()
                .collect(Collectors.toMap(CityModel::getId,
                        CityModel::getName));

        request.setAttribute("myRate", rateModel);
        LOG.debug(String.format("My rate --> %s", rateModel));
        request.setAttribute("citiesMap", citiesMap);
        LOG.debug(String.format("Cities map --> %s", citiesMap.toString()));
        request.setAttribute("distancesMap", distancesMap);
        LOG.debug(String.format("Distance map --> %s", distancesMap.toString()));
        request.setAttribute("ratesList", rates);
        LOG.debug(String.format("Rates list --> %s", rates.toString()));
    }

    private void handleRequest(HttpServletRequest request) {
        CityService cityService = (CityService) ServiceFactory.createService(ServiceType.CITY_SERVICE);
        RateService rateService = (RateService) ServiceFactory.createService(ServiceType.RATE_SERVICE);
        List<RateModel> rates;
        List<CityModel> cityModels;
        try {
            rates = new RateModelBuilder().create(rateService.findAll());
            cityModels = new CityModelBuilder().create(cityService.findAll());
        } catch (BuilderException | ServiceException e) {
            LOG.error(e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        Map<Integer, String> citiesMap = cityModels.stream()
                .collect(Collectors.toMap(CityModel::getId,
                        CityModel::getName));

        request.setAttribute("citiesMap", citiesMap);
        LOG.debug(String.format("Cities map --> %s", citiesMap.toString()));
        request.setAttribute("ratesList", rates);
        LOG.debug(String.format("Rates list --> %s", rates.toString()));
    }
}