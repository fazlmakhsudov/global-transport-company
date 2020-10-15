package com.epam.gtc.service_factory;

import com.epam.gtc.dao.*;
import com.epam.gtc.dao.entities.builders.*;
import com.epam.gtc.services.*;
import com.epam.gtc.services.domains.builders.*;
import com.epam.gtc.web.commands.CommandContainer;
import org.apache.log4j.Logger;

/**
 * Factory class,
 * creates instances of Services
 */
public class ServiceFactory {
    private static final Logger LOG = Logger.getLogger(CommandContainer.class);

    /**
     * Create Service intance
     *
     * @param serviceType service type
     * @return BaseService instance
     */
    public static BaseService createService(ServiceType serviceType) {
        LOG.debug(String.format("creating instance of %s", serviceType));
        switch (serviceType) {
            case CITY_SERVICE:
                return new CityServiceImpl(new MySQLCityDAOImpl(), new CityEntityBuilder(),
                        new CityDomainBuilderFromEntity());
            case DELIVERY_SERVICE:
                return new DeliveryServiceImpl(new MySQLDeliveryDAOImpl(), new DeliveryEntityBuilder(),
                        new DeliveryDomainBuilderFromEntity());

            case DISTANCE_SERVICE:
                return new DistanceServiceImpl(new MySQLDistanceDAOImpl(), new DistanceEntityBuilder(),
                        new DistanceDomainBuilderFromEntity());

            case INVOICE_SERVICE:
                return new InvoiceServiceImpl(new MySQLInvoiceDAOImpl(), new InvoiceEntityBuilder(),
                        new InvoiceDomainBuilderFromEntity());

            case RATE_SERVICE:
                return new RateServiceImpl(new MySQLRateDAOImpl(), new RateEntityBuilder(),
                        new RateDomainBuilderFromEntity());

            case REQUEST_SERVICE:
                return new RequestServiceImpl(new MySQLRequestDAOImpl(), new RequestEntityBuilder(),
                        new RequestDomainBuilderFromEntity());

            case USER_SERVICE:
                return new UserServiceImpl(new MySQLUserDAOImpl(), new UserEntityBuilder(),
                        new UserDomainBuilderFromEntity());
            default:
                LOG.error(String.format("error, no service ->> %s", serviceType));
                throw new EnumConstantNotPresentException(ServiceType.class, serviceType.name());
        }
    }

}
