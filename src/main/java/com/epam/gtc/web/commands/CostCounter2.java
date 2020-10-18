package com.epam.gtc.web.commands;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.DistanceService;
import com.epam.gtc.services.RateService;
import com.epam.gtc.services.RequestService;
import com.epam.gtc.services.domains.DistanceDomain;
import com.epam.gtc.services.domains.RateDomain;
import com.epam.gtc.services.factory.ServiceFactory;
import com.epam.gtc.services.factory.ServiceType;
import com.epam.gtc.web.models.RateModel;
import com.epam.gtc.web.models.RequestModel;
import com.epam.gtc.web.models.builders.RequestModelBuilder;
import org.apache.log4j.Logger;

import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Personal counter for request
 *
 * @author Fazliddin Makhsudov
 */
public class CostCounter2 {
    private static final Logger LOG = Logger.getLogger(CostCounter2.class);



    public static double countCost(int requestId) {
        RequestService requestService = (RequestService) ServiceFactory.createService(ServiceType.REQUEST_SERVICE);
        RequestModel requestModel = null;
        try {
            requestModel = new RequestModelBuilder().create(requestService.find(requestId));
        } catch (ServiceException | BuilderException e) {
            LOG.error(e.getMessage());
        }
        return countCost(requestModel);
    }

    /**
     * Counts personal cost for certain request
     *
     * @param requestModel request
     * @return cost
     */
    public static double countCost(RequestModel requestModel) {
       double distance = getDistance(requestModel);
       List<RateDomain> rates = getRates();
       Map<Integer, List<RateDomain>> appropriateRates = new HashMap<>();
       for (RateDomain rate : rates) {
           if (rate.getMaxDistance() < distance) {
               continue;
           }
           checkRequestWithRate(requestModel, appropriateRates, rate);
       }

    }

    private static void checkRequestWithRate(RequestModel requestModel, Map<Integer, List<RateDomain>> appropriateRates, RateDomain rate) {
        int difference = 0;
        if (rate.getMaxWeight() < requestModel.getWeight()) {
            difference++;
        }
        if (rate.getMaxLength() < requestModel.getLength()) {
            difference++;
        }
        if (rate.getMaxWidth() < requestModel.getWidth()) {
            difference++;
        }
        if (rate.getMaxHeight() < requestModel.getHeight()) {
            difference++;
        }
        if (difference <= 2) {
            if (appropriateRates.containsKey(difference)) {
                appropriateRates.get(difference).add(rate);
            } else {
                List<RateDomain> rateDomainList = new ArrayList<>();
                rateDomainList.add(rate);
                appropriateRates.put(difference, rateDomainList);
            }
        }
    }


    private static double getDistance(RequestModel requestModel) {
        DistanceService distanceService = (DistanceService) ServiceFactory.createService(ServiceType.DISTANCE_SERVICE);

        DistanceDomain distanceDomain = null;
        try {
            distanceDomain = distanceService.find(requestModel.getCityFromId(), requestModel.getCityToId());
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }
        return Objects.isNull(distanceDomain) ? 10d : distanceDomain.getDistance();
    }

    private  static List<RateDomain> getRates() {
        RateService rateService = (RateService) ServiceFactory.createService(ServiceType.RATE_SERVICE);
        try {
            return rateService.findAll();
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }
    }
}
