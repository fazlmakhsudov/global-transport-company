package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.CommandException;
import com.epam.gtc.services.CityService;
import com.epam.gtc.services.DistanceService;
import com.epam.gtc.web.models.CityModel;
import com.epam.gtc.web.models.DistanceModel;
import com.epam.gtc.web.models.builders.CityModelBuilder;
import com.epam.gtc.web.models.builders.DistanceModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Contact us command.
 *
 * @author Fazliddin Makhsudov
 */
public class UserCabinetCommand implements Command {

    private static final long serialVersionUID = 2735976616686657267L;
    private static final Logger LOG = Logger.getLogger(UserCabinetCommand.class);
    private final CityService cityService;
    private final DistanceService distanceService;


    public UserCabinetCommand(CityService cityService, DistanceService distanceService) {
        this.cityService = cityService;
        this.distanceService = distanceService;
    }

    @Override
    public final String execute(final HttpServletRequest request,
                                final HttpServletResponse response) {
        LOG.debug("Command starts");
        HttpSession session = request.getSession();
        Object sessionUser = session.getAttribute("user");
        LOG.trace("Session user --> " + sessionUser);
        String forward;
        if (Objects.isNull(sessionUser)) {
            forward = Path.COMMAND_INDEX;
            LOG.debug(String.format("forward --> %s", Path.COMMAND_INDEX));
        } else {
            session.removeAttribute("requeststab");
            session.removeAttribute("requeststabbody");
            session.removeAttribute("invoicestab");
            session.removeAttribute("invoicestabbody");
            session.removeAttribute("deliveriestab");
            session.removeAttribute("deliveriestabbody");
            session.setAttribute("profiletab", "active");
            session.setAttribute("profiletabbody", "show active");
            forward = Path.PAGE_USER_CABINET;
            LOG.debug(String.format("forward --> %s", Path.PAGE_USER_CABINET));
        }
        supplyRequestWithCities(request);
        LOG.debug("Command finished");
        return forward;
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