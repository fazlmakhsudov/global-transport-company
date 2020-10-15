package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.MySQLRateDAOImpl;
import com.epam.gtc.dao.entities.builders.RateEntityBuilder;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.CommandException;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.CityService;
import com.epam.gtc.services.RateService;
import com.epam.gtc.services.RateServiceImpl;
import com.epam.gtc.services.domains.builders.RateDomainBuilderFromEntity;
import com.epam.gtc.services.factory.ServiceFactory;
import com.epam.gtc.services.factory.ServiceType;
import com.epam.gtc.web.models.CityModel;
import com.epam.gtc.web.models.RateModel;
import com.epam.gtc.web.models.builders.CityModelBuilder;
import com.epam.gtc.web.models.builders.RateModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Index command.
 *
 * @author Fazliddin Makhsudov
 */
public class IndexCommand implements Command {
    private static final long serialVersionUID = -3071536593627692473L;
    private static final Logger LOG = Logger.getLogger(IndexCommand.class);
    private final RateService rateService;

    // TODO create constructor in every command
    public IndexCommand(RateService rateService) {
        this.rateService = rateService;
    }

    public IndexCommand() {
        this.rateService = new RateServiceImpl(new MySQLRateDAOImpl(), new RateEntityBuilder(),
                new RateDomainBuilderFromEntity());

    }

    @Override
    public final String execute(final HttpServletRequest request,
                                final HttpServletResponse response) {
        LOG.debug("Command starts");
        LOG.trace("Set request attribute: command index");
        getRates(request);
        getCities(request);
        LOG.debug("Command finished");
        return Path.PAGE_HOME;
    }

    private void getRates(HttpServletRequest request) {
        List<RateModel> rates;
        try {
            rates = new RateModelBuilder().create(rateService.findAll());
        } catch (ServiceException | BuilderException e) {
            LOG.error(e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        request.setAttribute("command", "index");
        request.setAttribute("ratesList", rates);
    }

    private void getCities(HttpServletRequest request) {
        CityService cityService = (CityService) ServiceFactory.createService(ServiceType.CITY_SERVICE);
        try {
            List<CityModel> cityModels = new CityModelBuilder().create(cityService.findAll());
            List<String> cityNames = cityModels.stream()
                    .map(CityModel::getName)
                    .collect(Collectors.toList());
            Map<Integer, String> citiesMap = cityModels.stream()
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