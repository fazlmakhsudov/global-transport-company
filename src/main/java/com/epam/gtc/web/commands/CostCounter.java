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
       Double cost;
        if (appropriateRates.get(0).size() > 0) {
           System.out.println("1 F");
            cost = appropriateRates.get(0).stream().map(RateDomain::getCost).min(Double::compareTo).get();
       } else if (appropriateRates.get(1).size() > 0) {
           System.out.println("2 F");
           cost = appropriateRates.get(0).stream().map(RateDomain::getCost).min(Double::compareTo).get();
           cost *= fineFor1Criteria;
       } else if () {
           System.out.println("3 F");
           cost = appropriateRates.get(0).stream().map(RateDomain::getCost).min(Double::compareTo).get();
           cost *= fineFor2Criteria;
       } else if (appropriateRates.size() == 0) {
            System.out.println("0 F");
            RateDomain rate = rates.get(rates.size() - 1);
            cost = rate.getCost();

            double fineCost = rate.getCost() * fineForCub;
            System.out.println("cost " + cost);
            System.out.println("finecost " + fineCost);
            double fineWeight = rate.getMaxWeight() * fineForWeight;
            System.out.println("fineweight " + fineWeight);
            double rateCub = rate.getMaxLength() * rate.getMaxWidth() * rate.getMaxHeight() * 0.000001d;
            System.out.println("ratecub " + rateCub);
            double requestCub = requestModel.getLength() * requestModel.getWidth() * requestModel.getHeight() * 0.000001d;
            System.out.println("requestcub " + requestCub);
            while (rateCub < requestCub) {
                cost += fineCost;
                rateCub += 0.05d;
            }
            System.out.println("rate cub ** " + rateCub);
            System.out.println("cost " + cost);
            double rateWeight = rate.getMaxWeight();
            System.out.println("rate weight " + rateWeight);
            while (rateWeight < requestModel.getWeight()) {
                cost += fineWeight;
                rateWeight += 1;
            }
            System.out.println("rateweight ** " + rateWeight);
            System.out.println("cost **** " + cost);
        }
        System.out.println(" FF cost " + cost);
       LOG.trace(String.format("Counting is completed with value %.3f", cost));
       return cost;
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
        return Objects.isNull(distanceDomain) ? 10d : distanceDomain.getDistance();
    }

    private  static List<RateDomain> getRates() {
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
