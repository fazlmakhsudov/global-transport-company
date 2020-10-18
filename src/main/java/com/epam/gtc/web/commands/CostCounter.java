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
import com.epam.gtc.web.models.RequestModel;
import com.epam.gtc.web.models.builders.RequestModelBuilder;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Personal counter for request
 *
 * @author Fazliddin Makhsudov
 */
public class CostCounter {
    private static final Logger LOG = Logger.getLogger(CostCounter.class);
    private static final Double fineFor1Criteria = 1.4d;
    private static final Double fineFor2Criteria = 1.6d;

    private static final Double fineForCub = 0.05d;
    private static final Double fineForWeight = 0.05d;


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
        LOG.trace("Start counting cost for request: ");
        LOG.trace(requestModel);
        double distance = getDistance(requestModel);
        LOG.trace(String.format("Distance between %s and %s is %.2f", requestModel.getCityFromId(),
                requestModel.getCityToId(), distance));
        List<RateDomain> rates = getRates();
        LOG.trace(String.format("Sorted on cost all rates : %s", rates));
        Map<Integer, List<RateDomain>> appropriateRates = new HashMap<>();
        for (RateDomain rate : rates) {
            if (rate.getMaxDistance() < distance) {
                continue;
            }
            checkRequestWithRate(requestModel, appropriateRates, rate);
        }
        LOG.trace(String.format("Appropriate rates : %s", appropriateRates));
        List<Double> costs = new ArrayList<>();
        if (appropriateRates.containsKey(0) && appropriateRates.get(0).size() > 0) {

            double cost = appropriateRates.get(0).stream().map(RateDomain::getCost).min(Double::compareTo).get();
            costs.add(cost);
        }
        if (appropriateRates.containsKey(1) && appropriateRates.get(1).size() > 0) {

            double cost = appropriateRates.get(1).stream().map(RateDomain::getCost).min(Double::compareTo).get();
            cost *= fineFor1Criteria;

            costs.add(cost);

        }
        if (appropriateRates.containsKey(2) && appropriateRates.get(2).size() > 0) {

            double cost = appropriateRates.get(2).stream().map(RateDomain::getCost).min(Double::compareTo).get();
            cost *= fineFor2Criteria;
            costs.add(cost);
        }
        if (appropriateRates.size() == 0) {

            RateDomain rate = rates.get(rates.size() - 1);
            double cost = rate.getCost();

            double fineCost = rate.getCost() * fineForCub;

            double fineWeight = rate.getMaxWeight() * fineForWeight;

            double rateCub = rate.getMaxLength() * rate.getMaxWidth() * rate.getMaxHeight() * 0.000001d;

            double requestCub = requestModel.getLength() * requestModel.getWidth() * requestModel.getHeight() * 0.000001d;

            while (rateCub < requestCub) {
                cost += fineCost;
                rateCub += 0.05d;
            }

            double rateWeight = rate.getMaxWeight();

            while (rateWeight < requestModel.getWeight()) {
                cost += fineWeight;
                rateWeight += 1;
            }
            costs.add(cost);

        }

        LOG.trace(String.format("Counting is completed with value %.3f", costs.stream().min(Double::compareTo).get()));
        return costs.stream().min(Double::compareTo).get();
    }

    private static void checkRequestWithRate(RequestModel requestModel, Map<Integer, List<RateDomain>> appropriateRates, RateDomain rate) {
        int difference = 0;
        difference = checkCriterias(requestModel, rate, difference);
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

    private static int checkCriterias(RequestModel requestModel, RateDomain rate, int difference) {
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
        return difference;
    }


    private static double getDistance(RequestModel requestModel) {
        DistanceService distanceService = (DistanceService) ServiceFactory.createService(ServiceType.DISTANCE_SERVICE);

        DistanceDomain distanceDomain = null;
        try {
            distanceDomain = distanceService.find(requestModel.getCityFromId(), requestModel.getCityToId());
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }
        return Objects.isNull(distanceDomain) ? 300d : distanceDomain.getDistance();
    }

    private static List<RateDomain> getRates() {
        RateService rateService = (RateService) ServiceFactory.createService(ServiceType.RATE_SERVICE);
        try {
            List<RateDomain> rates = rateService.findAll();
            rates.sort(Comparator.comparingDouble(RateDomain::getCost));
            return rates;
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }
        return new ArrayList<>();
    }

}
