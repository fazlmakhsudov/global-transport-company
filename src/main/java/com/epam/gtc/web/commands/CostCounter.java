package com.epam.gtc.web.commands;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.DistanceService;
import com.epam.gtc.services.RequestService;
import com.epam.gtc.services.domains.DistanceDomain;
import com.epam.gtc.services.factory.ServiceFactory;
import com.epam.gtc.services.factory.ServiceType;
import com.epam.gtc.web.models.RequestModel;
import com.epam.gtc.web.models.builders.RequestModelBuilder;
import org.apache.log4j.Logger;

import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * Personal counter for request
 *
 * @author Fazliddin Makhsudov
 */
public class CostCounter {
    private static final Logger LOG = Logger.getLogger(CostCounter.class);
    //Truck Scania
    private static final double litresPerKm = 23d / 100; // 100 km concsumes 23 litres approximately
    private static final double pricePerKm = litresPerKm * 22; // 22UAH 1 litres of A92 petrolium
    private static final double permittedSpeed = 110d; // 110 km for 2020 in Ukraine
    private static final double truckVolume = 87.75d;
    private static final double carryingCapacity = 20d;
    // Company interest rate
    private static final double companyInterestRate = 3.6d; //
    // Company bonus if requested delivery date is earlier standard delivery date \
    private static final double companyBonus = 0.2d;
    // Riding hours per day
    private static final int ridingHours = 10;

    static {
        LOG.debug(String.format("initializing pricePerKm = %.2f", pricePerKm));
        LOG.debug(String.format("initializing averageSpeed = %.2f", permittedSpeed));
        LOG.debug(String.format("initializing truckVolume = %.2f", truckVolume));
        LOG.debug(String.format("initializing carryingCapacity = %.2f", carryingCapacity));
    }

    public static Date determineDeliveryDate(RequestModel requestModel) {
        double hours = getDistance(requestModel) / permittedSpeed;
        int days = (int) hours / ridingHours + (((int) hours % ridingHours) < 5 ? 0 : 1);
        LOG.debug(String.format("estimated days for standard delivery --> %d", days));
        LocalDate localDate = LocalDate.now().plusDays(days);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date deliveryDate = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        LOG.debug(String.format("estimated delivery date --> %s", deliveryDate.toString()));
        return deliveryDate;
    }

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

    public static double countCost(RequestModel requestModel) {
        double distance = getDistance(requestModel);
        LOG.debug(String.format("distance --> %.2f", distance));
        double cost = distance * getCostPerKm(requestModel); // this is cost for normal delivery,
        LOG.debug(String.format("estimated cost for standard delivery --> %.3f", cost));
        double hours = distance / permittedSpeed;
        LOG.debug(String.format("estimated riding hours for delivery --> %.3f", hours));
        int standartDeliveryDays = (int) hours / ridingHours + (((int) hours % ridingHours) < 5 ? 0 : 1);
        LOG.debug(String.format("estimated days for standard delivery --> %d", standartDeliveryDays));
        LocalDate localDate = LocalDate.now().plusDays(standartDeliveryDays);

        Date requestedDeliveryDate = requestModel.getDeliveryDate();
        LOG.debug(String.format("Requested delivery date --> %s", requestedDeliveryDate));
        Instant current = requestedDeliveryDate.toInstant();
        LocalDate requestedDeliveryLocalDate = LocalDateTime.ofInstant(current, ZoneId.systemDefault()).toLocalDate();
        int comparedDays = requestedDeliveryLocalDate.compareTo(localDate); // deviation from standart delivery days

        LOG.debug(String.format("deviation from standart delivery days --> %d", comparedDays));

        double deliveryDateRate = 1;
        if (comparedDays <= 0) {
            deliveryDateRate = (-1 * comparedDays + standartDeliveryDays) / standartDeliveryDays;
        } else {
            deliveryDateRate -= companyBonus;
        }
        LOG.debug(String.format("delivery date rate  --> %.3f", deliveryDateRate));

        // if the requested date more than standart delivery date than cost is supposed to be increased
        double correctedCost = deliveryDateRate * cost;
        LOG.debug(String.format("corrected cost for delivery current request --> %.2f", correctedCost));
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        return Double.parseDouble(nf.format(correctedCost));
    }


    private static double getCostPerKm(RequestModel requestModel) {
        double volumeOfCargo = requestModel.getHeight() * requestModel.getWidth()
                * requestModel.getLength() * 0.000001d; // in m^3
        LOG.debug(String.format("volume of cargo --> %.3f m^3", volumeOfCargo));


        double cub = carryingCapacity / truckVolume; // ? weight tonne in 1 m^3 according to truck properties
        LOG.debug(String.format("estimated permitted weight for 1 m^3 --> %.3f", cub));

        double cargoWeight = requestModel.getWeight() / 1000; // tonne
        LOG.debug(String.format("cargo weight --> %.3f tonnes", cargoWeight));

        double advancedWeightForCargo = cub * volumeOfCargo; // determine how much is applicable for cargoVolume
        while (advancedWeightForCargo < cargoWeight) { // increase admittedWeight while it equals approximately to cargoWeight
            volumeOfCargo += 0.1;
            advancedWeightForCargo = cub * volumeOfCargo;
        }
        LOG.debug(String.format("advanced weight --> %.3f", advancedWeightForCargo));
        double pricePerKmForCargo = (advancedWeightForCargo / carryingCapacity) * pricePerKm; // count price per km for current cargo
        LOG.debug(String.format("estimated price for cargo --> %.4f money", pricePerKmForCargo));
        pricePerKmForCargo = pricePerKmForCargo * companyInterestRate;
        LOG.debug(String.format("estimated price for cargo with company rate interests --> %.4f money", pricePerKmForCargo));
        return pricePerKmForCargo;
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
}
