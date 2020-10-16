package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.CommandException;
import com.epam.gtc.services.CityService;
import com.epam.gtc.web.models.CityModel;
import com.epam.gtc.web.models.builders.CityModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contact us command.
 *
 * @author Fazliddin Makhsudov
 */
public class ContactUsCommand implements Command {

    private static final long serialVersionUID = 2735976616686657267L;
    private static final Logger LOG = Logger.getLogger(ContactUsCommand.class);
    private final CityService cityService;


    public ContactUsCommand(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public final String execute(final HttpServletRequest request,
                                final HttpServletResponse response) {
        LOG.debug("Command starts");
        supplyRequestWithCities(request);
        LOG.debug(String.format("forward --> %s", Path.PAGE_CONTACT_US));
        LOG.debug("Command finished");
        return Path.PAGE_CONTACT_US;
    }

    private void supplyRequestWithCities(HttpServletRequest request) {
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