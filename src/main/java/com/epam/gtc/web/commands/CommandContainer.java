package com.epam.gtc.web.commands;

import com.epam.gtc.services.*;
import com.epam.gtc.services.factory.ServiceFactory;
import com.epam.gtc.services.factory.ServiceType;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holder for all commands.<br/>
 *
 * @author Fazliddin Makhsudov
 */
public final class CommandContainer {

    private static final Logger LOG = Logger.getLogger(CommandContainer.class);
    /**
     * Commands.
     */
    private static final Map<String, Command> commands =
            new TreeMap<>();

    static {
        // common commands
        commands.put("index", new IndexCommand((RateService) ServiceFactory.createService(ServiceType.RATE_SERVICE),
                (CityService) ServiceFactory.createService(ServiceType.CITY_SERVICE),
                (DistanceService) ServiceFactory.createService(ServiceType.DISTANCE_SERVICE)));
        commands.put("login", new LoginCommand((UserService) ServiceFactory.createService(ServiceType.USER_SERVICE)));
        commands.put("logout", new LogoutCommand());
        commands.put("signup", new SignupCommand((UserService) ServiceFactory.createService(ServiceType.USER_SERVICE)));
        commands.put("rates", new RatesCommand((CityService) ServiceFactory.createService(ServiceType.CITY_SERVICE),
                (DistanceService) ServiceFactory.createService(ServiceType.DISTANCE_SERVICE),
                (RateService) ServiceFactory.createService(ServiceType.RATE_SERVICE)));
        commands.put("gallery", new GalleryCommand((CityService) ServiceFactory.createService(ServiceType.CITY_SERVICE),
                (DistanceService) ServiceFactory.createService(ServiceType.DISTANCE_SERVICE)));
        commands.put("aboutUs", new AboutUsCommand((CityService) ServiceFactory.createService(ServiceType.CITY_SERVICE),
                (DistanceService) ServiceFactory.createService(ServiceType.DISTANCE_SERVICE)));
        commands.put("contactUs", new ContactUsCommand((CityService) ServiceFactory.createService(ServiceType.CITY_SERVICE),
                (DistanceService) ServiceFactory.createService(ServiceType.DISTANCE_SERVICE)));

        commands.put("deliveryMap", new DeliveryMapPageCommand((DistanceService) ServiceFactory.createService(ServiceType.DISTANCE_SERVICE),
                (CityService) ServiceFactory.createService(ServiceType.CITY_SERVICE),
                (RateService) ServiceFactory.createService(ServiceType.RATE_SERVICE)));

        commands.put("userCabinet", new UserCabinetCommand((CityService) ServiceFactory.createService(ServiceType.CITY_SERVICE),
                (DistanceService) ServiceFactory.createService(ServiceType.DISTANCE_SERVICE)));
        commands.put("userProfileTab", new UserProfileTabCommand((UserService) ServiceFactory.createService(ServiceType.USER_SERVICE)));
        commands.put("userRequestsTab", new UserRequestsTabCommand((RequestService) ServiceFactory.createService(ServiceType.REQUEST_SERVICE),
                (CityService) ServiceFactory.createService(ServiceType.CITY_SERVICE),
                (DistanceService) ServiceFactory.createService(ServiceType.DISTANCE_SERVICE)));
        commands.put("userInvoicesTab", new UserInvoicesTabCommand((InvoiceService) ServiceFactory.createService(ServiceType.INVOICE_SERVICE)));
        commands.put("userDeliveriesTab", new UserDeliveriesTabCommand((DeliveryService) ServiceFactory.createService(ServiceType.DELIVERY_SERVICE)));

        commands.put("personalCounterForm", new PersonalCounterFormCommand());

        commands.put("noCommand", new NoCommand());
        commands.put("adminMainPage", new AdminMainPageCommand((RequestService) ServiceFactory.createService(ServiceType.REQUEST_SERVICE),
                (InvoiceService) ServiceFactory.createService(ServiceType.INVOICE_SERVICE),
                (DeliveryService) ServiceFactory.createService(ServiceType.DELIVERY_SERVICE)));
        commands.put("adminUsersPage", new AdminUsersPageCommand((UserService) ServiceFactory.createService(ServiceType.USER_SERVICE)));
        commands.put("adminCitiesPage", new AdminCitiesPageCommand((CityService) ServiceFactory.createService(ServiceType.CITY_SERVICE)));
        commands.put("adminDeliveriesPage", new AdminDeliveriesPageCommand((DeliveryService) ServiceFactory.createService(ServiceType.DELIVERY_SERVICE)));
        commands.put("adminDistancesPage", new AdminDistancesPageCommand((DistanceService) ServiceFactory.createService(ServiceType.DISTANCE_SERVICE),
                (CityService) ServiceFactory.createService(ServiceType.CITY_SERVICE)));
        commands.put("adminInvoicesPage", new AdminInvoicesPageCommand((InvoiceService) ServiceFactory.createService(ServiceType.INVOICE_SERVICE)));
        commands.put("adminRatesPage", new AdminRatesPageCommand((RateService) ServiceFactory.createService(ServiceType.RATE_SERVICE)));
        commands.put("adminRequestsPage", new AdminRequestsPageCommand((RequestService) ServiceFactory.createService(ServiceType.REQUEST_SERVICE),
                (CityService) ServiceFactory.createService(ServiceType.CITY_SERVICE),(DistanceService) ServiceFactory.createService(ServiceType.DISTANCE_SERVICE)));

        LOG.debug("Command container was successfully initialized");
        LOG.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static Command get(final String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }

    private CommandContainer() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
}