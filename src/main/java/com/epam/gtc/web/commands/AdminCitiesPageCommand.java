package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.services.CityService;
import com.epam.gtc.services.domains.CityDomain;
import com.epam.gtc.utils.Method;
import com.epam.gtc.web.models.CityModel;
import com.epam.gtc.web.models.builders.CityModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * Admin cities page command.
 *
 * @author Fazliddin Makhsudov
 */
public class AdminCitiesPageCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(AdminCitiesPageCommand.class);
    private final CityService cityService;


    public AdminCitiesPageCommand(CityService cityService) {
        this.cityService = cityService;
    }


    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws AppException {
        LOG.debug("AdminCitiesPageCommand starts");
        String forward = handleRequest(request);
        LOG.debug("AdminCitiesPageCommand finished");
        return forward;
    }

    private String handleRequest(final HttpServletRequest request) throws AppException {
        int citiesNumber = cityService.countAllCities();
        LOG.trace("Number of Cities : " + citiesNumber);
        Optional<String> optionalPage = Optional.ofNullable(request.getParameter("page"));
        LOG.trace("optional page : " + optionalPage);
        Optional<String> optionalItemsPerPage = Optional.ofNullable(request.getParameter("itemsPerPage"));
        LOG.trace("optional items per page : " + optionalItemsPerPage);

        int page = optionalPage.isPresent() && Validator.isValidNumber(optionalPage.get())
                ? optionalPage.map(Integer::parseInt).orElse(1) : 1;

        int itemsPerPage = optionalItemsPerPage.isPresent() && Validator.isValidNumber(optionalItemsPerPage.get()) ?
                optionalItemsPerPage.map(Integer::parseInt).orElse(5) : 5;

        String forward = Path.PAGE_ADMIN_CITIES;
        if (Method.isPost(request)) {
            request.getSession().removeAttribute("errorCity");
            forward = doPost(request, cityService, page, itemsPerPage);
        }
        List<CityModel> cityModels = new CityModelBuilder().create(cityService.findAll(page, itemsPerPage));
        LOG.trace("Cities : " + cityModels);
        request.setAttribute("adminCities", cityModels);
        request.setAttribute("page", page);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("citiesNumber", citiesNumber);
        return forward;
    }

    private String doPost(HttpServletRequest request, CityService cityService, int page, int itemsPerPage) throws com.epam.gtc.exceptions.ServiceException {
        String forward;
        StringBuilder errorCity = new StringBuilder();
        boolean errorFlag = false;

        LOG.trace("Method is Post");
        String action = request.getParameter(FormRequestParametersNames.ACTION);
        LOG.trace("Action --> " + action);
        if (!Validator.isValidString(action)) {
            errorFlag = true;
            errorCity.append("Invalid action").append("<br/>");
        }

        boolean removeFlag = action.equalsIgnoreCase("remove");

        String cityIdString = request.getParameter(FormRequestParametersNames.CITY_ID);
        LOG.trace("City id --> " + cityIdString);
        if (!action.equalsIgnoreCase("add") && !Validator.isValidNumber(cityIdString)) {
            errorFlag = true;
            errorCity.append("Invalid city id").append("<br/>");
        }
        String name = request.getParameter(FormRequestParametersNames.CITY_NAME);
        LOG.trace("City name --> " + name);
        if (!Validator.isValidString(name) && !removeFlag) {
            errorFlag = true;
            errorCity.append("Invalid city name").append("<br/>");
        }

        if (errorFlag) {
            request.getSession().setAttribute("errorCity", errorCity.toString());
            return String.format("%s&page=%s&itemsPerPage=%s", Path.COMMAND_ADMIN_CITIES_PAGE,
                    page, itemsPerPage);
        }

        int cityId = action.equalsIgnoreCase("add") ? -1 : Integer.parseInt(cityIdString);

        switch (action) {
            case "add":
                if (Validator.isValidString(name) && Objects.isNull(cityService.find(name))) {
                    CityDomain newCity = new CityDomain();
                    newCity.setName(name);
                    int newId = cityService.add(newCity);
                    LOG.trace("Added status(new id) --> " + newId);
                } else {
                    LOG.error("Invalid city name or such city exists already.");
                    request.getSession().setAttribute("errorCity", "Invalid city name or such city exists already.");
                }
                break;
            case "save":
                CityDomain cityDomain = cityService.find(cityId);
                cityDomain.setName(name);
                boolean savedFlag = cityService.save(cityDomain);
                LOG.trace("Saved status --> " + savedFlag);
                break;
            case "remove":
                boolean removedFlag = cityService.remove(cityId);
                LOG.trace("Removed status --> " + removedFlag);
                break;
        }
        forward = String.format("%s&page=%s&itemsPerPage=%s", Path.COMMAND_ADMIN_CITIES_PAGE,
                page, itemsPerPage);
        return forward;
    }
}