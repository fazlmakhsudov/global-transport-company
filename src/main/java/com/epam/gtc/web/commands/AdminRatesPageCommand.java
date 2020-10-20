package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.services.RateService;
import com.epam.gtc.services.domains.RateDomain;
import com.epam.gtc.utils.Method;
import com.epam.gtc.web.models.RateModel;
import com.epam.gtc.web.models.builders.RateModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


/**
 * Admin Rate page command.
 *
 * @author Fazliddin Makhsudov
 */
public class AdminRatesPageCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(AdminRatesPageCommand.class);
    private final RateService rateService;

    public AdminRatesPageCommand(RateService rateService) {
        this.rateService = rateService;
    }

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws AppException {
        LOG.debug("AdminRatesPageCommand starts");
        String forward = handleRequest(request);
        LOG.debug("AdminRatesPageCommand finished");
        return forward;
    }

    private String handleRequest(final HttpServletRequest request) throws AppException {

        int ratesNumber = rateService.countAllRates();
        LOG.trace("Number of rates : " + ratesNumber);
        Optional<String> optionalPage = Optional.ofNullable(request.getParameter("page"));
        LOG.trace("optional page : " + optionalPage);
        Optional<String> optionalItemsPerPage = Optional.ofNullable(request.getParameter("itemsPerPage"));
        LOG.trace("optional items per page : " + optionalItemsPerPage);

        int page = optionalPage.isPresent() && Validator.isValidNumber(optionalPage.get())
                ? optionalPage.map(Integer::parseInt).orElse(1) : 1;

        int itemsPerPage = optionalItemsPerPage.isPresent() && Validator.isValidNumber(optionalItemsPerPage.get()) ?
                optionalItemsPerPage.map(Integer::parseInt).orElse(5) : 5;

        String forward = Path.PAGE_ADMIN_RATES;
        if (Method.isPost(request)) {
            forward = doPost(request, rateService, page, itemsPerPage);
        }
        List<RateModel> rateModels = new RateModelBuilder().create(rateService.findAll(page, itemsPerPage));
        LOG.trace("Rates : " + rateModels);
        request.setAttribute("page", page);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("ratesNumber", ratesNumber);
        request.setAttribute("adminRates", rateModels);
        return forward;
    }

    private String doPost(HttpServletRequest request, RateService rateService, int page, int itemsPerPage) throws com.epam.gtc.exceptions.ServiceException {
        String forward = String.format("%s&page=%s&itemsPerPage=%s", Path.COMMAND_ADMIN_RATES_PAGE,
                page, itemsPerPage);
        StringBuilder errorRates = new StringBuilder();
        boolean errorFlag = false;

        LOG.trace("Method is Post");
        request.getSession().removeAttribute("errorRates");
        String action = request.getParameter(FormRequestParametersNames.ACTION);
        LOG.trace("Action --> " + action);
        if (!Validator.isValidString(action)) {
            errorFlag = true;
            errorRates.append("Invalid action").append("<br/>");
        }

        boolean isRemoveMethod = action.equalsIgnoreCase("remove");

        String name = isRemoveMethod ? "" :
                request.getParameter(FormRequestParametersNames.RATE_NAME);
        LOG.trace("Rate name --> " + name);
        if (!isRemoveMethod && !Validator.isValidString(name)) {
            errorFlag = true;
            errorRates.append("Invalid name").append("<br/>");
        }

        String maxWeightString = request.getParameter(FormRequestParametersNames.RATE_MAX_WEIGHT);
        LOG.trace("Rate max weight --> " + maxWeightString);
        if (!isRemoveMethod && !Validator.isValidNumber(maxWeightString)) {
            errorFlag = true;
            errorRates.append("Invalid weight").append("<br/>");
        }

        String maxLengthString = request.getParameter(FormRequestParametersNames.RATE_MAX_LENGTH);
        LOG.trace("Rate max length --> " + maxLengthString);
        if (!isRemoveMethod && !Validator.isValidNumber(maxLengthString)) {
            errorFlag = true;
            errorRates.append("Invalid length").append("<br/>");
        }

        String maxWidthString = request.getParameter(FormRequestParametersNames.RATE_MAX_WIDTH);
        LOG.trace("Rate max width --> " + maxWidthString);
        if (!isRemoveMethod && !Validator.isValidNumber(maxWidthString)) {
            errorFlag = true;
            errorRates.append("Invalid width").append("<br/>");
        }

        String maxHeightString = request.getParameter(FormRequestParametersNames.RATE_MAX_HEIGHT);
        LOG.trace("Rate max height --> " + maxHeightString);
        if (!isRemoveMethod && !Validator.isValidNumber(maxHeightString)) {
            errorFlag = true;
            errorRates.append("Invalid height").append("<br/>");
        }

        String maxDistanceString = request.getParameter(FormRequestParametersNames.RATE_MAX_DISTANCE);
        LOG.trace("Rate max distance --> " + maxDistanceString);
        if (!isRemoveMethod && !Validator.isValidNumber(maxDistanceString)) {
            errorFlag = true;
            errorRates.append("Invalid distance").append("<br/>");
        }

        String costString = request.getParameter(FormRequestParametersNames.RATE_COST);
        LOG.trace("Rate cost --> " + costString);
        if (!isRemoveMethod && !Validator.isValidNumber(costString)) {
            errorFlag = true;
            errorRates.append("Invalid cost").append("<br/>");
        }

        String rateIdString = request.getParameter(FormRequestParametersNames.RATE_ID);
        LOG.trace("Rate id --> " + rateIdString);
        if (!Validator.isValidNumber(rateIdString) && !action.equalsIgnoreCase("add")) {
            errorFlag = true;
            errorRates.append("Invalid rate id").append("<br/>");
        }

        if (errorFlag) {
            request.getSession().setAttribute("errorRates", errorRates.toString());
            return forward;
        }

        doPostIfAllRight(rateService, action, isRemoveMethod, name, maxWeightString, maxLengthString, maxWidthString, maxHeightString, maxDistanceString, costString, rateIdString);

        return forward;
    }

    private void doPostIfAllRight(RateService rateService, String action, boolean isRemoveMethod, String name, String maxWeightString, String maxLengthString, String maxWidthString, String maxHeightString, String maxDistanceString, String costString, String rateIdString) throws com.epam.gtc.exceptions.ServiceException {
        double maxWidth = isRemoveMethod ? -1d : Double.parseDouble(maxWidthString);
        double maxHeight = isRemoveMethod ? -1d : Double.parseDouble(maxHeightString);
        double maxLength = isRemoveMethod ? -1d : Double.parseDouble(maxLengthString);
        double maxWeight = isRemoveMethod ? -1d : Double.parseDouble(maxWeightString);
        double maxDistance = isRemoveMethod ? -1d : Double.parseDouble(maxDistanceString);
        double cost = isRemoveMethod ? -1d : Double.parseDouble(costString);
        int rateId = action.equalsIgnoreCase("add") ? -1 : Integer.parseInt(rateIdString);
        switch (action) {
            case "add":
                RateDomain newRateDomain = new RateDomain();
                newRateDomain.setName(name);
                newRateDomain.setMaxWeight(maxWeight);
                newRateDomain.setMaxLength(maxLength);
                newRateDomain.setMaxWidth(maxWidth);
                newRateDomain.setMaxHeight(maxHeight);
                newRateDomain.setMaxDistance(maxDistance);
                newRateDomain.setCost(cost);
                int newId = rateService.add(newRateDomain);
                LOG.trace("Added status(new id) --> " + newId);
                break;
            case "save":
                RateDomain rateDomain = rateService.find(rateId);
                rateDomain.setName(name);
                rateDomain.setMaxWeight(maxWeight);
                rateDomain.setMaxLength(maxLength);
                rateDomain.setMaxWidth(maxWidth);
                rateDomain.setMaxHeight(maxHeight);
                rateDomain.setMaxDistance(maxDistance);
                rateDomain.setCost(cost);
                boolean savedFlag = rateService.save(rateDomain);
                LOG.trace("Saved status --> " + savedFlag);
                break;
            case "remove":
                boolean removedFlag = rateService.remove(rateId);
                LOG.trace("Removed status --> " + removedFlag);
                break;

        }
    }

}