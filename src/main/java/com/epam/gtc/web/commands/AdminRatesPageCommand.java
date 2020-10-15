package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.service_factory.ServiceFactory;
import com.epam.gtc.service_factory.ServiceType;
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
 */
public class AdminRatesPageCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(AdminRatesPageCommand.class);

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws AppException {
        LOG.debug("AdminRatesPageCommand starts");
        String forward = handleRequest(request);
        LOG.debug("AdminRatesPageCommand finished");
        return forward;
    }

    private String handleRequest(final HttpServletRequest request) throws AppException {
        RateService rateService = (RateService) ServiceFactory.createService(ServiceType.RATE_SERVICE);
        int ratesNumber = rateService.countAllRates();
        LOG.trace("Number of rates : " + ratesNumber);
        Optional<String> optionalPage = Optional.ofNullable(request.getParameter("page"));
        LOG.trace("optional page : " + optionalPage);
        Optional<String> optionalItemsPerPage = Optional.ofNullable(request.getParameter("itemsPerPage"));
        LOG.trace("optional items per page : " + optionalItemsPerPage);
        int page = optionalPage.map(Integer::parseInt).orElse(1);
        int itemsPerPage = optionalItemsPerPage.map(Integer::parseInt).orElse(5);
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
        String forward;
        LOG.trace("Method is Post");
        String action = request.getParameter(FormRequestParameter.ACTION);
        LOG.trace("Action --> " + action);

        String name = action.equalsIgnoreCase("remove") ? "" :
                request.getParameter(FormRequestParameter.RATE_NAME);
        LOG.trace("Rate name --> " + name);
        double maxWeight = action.equalsIgnoreCase("remove") ? -1d :
                Double.parseDouble(request.getParameter(FormRequestParameter.RATE_MAX_WEIGHT));
        LOG.trace("Rate max weight --> " + maxWeight);
        double maxLength = action.equalsIgnoreCase("remove") ? -1d :
                Double.parseDouble(request.getParameter(FormRequestParameter.RATE_MAX_LENGTH));
        LOG.trace("Rate max length --> " + maxLength);
        double maxWidth = action.equalsIgnoreCase("remove") ? -1d :
                Double.parseDouble(request.getParameter(FormRequestParameter.RATE_MAX_WIDTH));
        LOG.trace("Rate max width --> " + maxWidth);
        double maxHeight = action.equalsIgnoreCase("remove") ? -1d :
                Double.parseDouble(request.getParameter(FormRequestParameter.RATE_MAX_HEIGHT));
        LOG.trace("Rate max height --> " + maxHeight);
        double maxDistance = action.equalsIgnoreCase("remove") ? -1d :
                Double.parseDouble(request.getParameter(FormRequestParameter.RATE_MAX_DISTANCE));
        LOG.trace("Rate max distance --> " + maxDistance);
        double cost = action.equalsIgnoreCase("remove") ? -1d :
                Double.parseDouble(request.getParameter(FormRequestParameter.RATE_COST));
        LOG.trace("Rate cost --> " + cost);

        int rateId = action.equalsIgnoreCase("add") ? -1 :
                Integer.parseInt(request.getParameter(FormRequestParameter.RATE_ID));
        LOG.trace("Rate id --> " + rateId);


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
            default:
                //TODO no action error
        }
        forward = String.format("%s&page=%s&itemsPerPage=%s", Path.COMMAND_ADMIN_RATES_PAGE,
                page, itemsPerPage);
        return forward;
    }

}