package com.epam.gtc.web.listeners;

import com.epam.gtc.dao.MySQLCityDAOImpl;
import com.epam.gtc.dao.MySQLRateDAOImpl;
import com.epam.gtc.dao.entities.builders.CityEntityBuilder;
import com.epam.gtc.dao.entities.builders.RateEntityBuilder;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.services.CityService;
import com.epam.gtc.services.CityServiceImpl;
import com.epam.gtc.services.RateService;
import com.epam.gtc.services.RateServiceImpl;
import com.epam.gtc.services.domains.builders.CityDomainBuilderFromEntity;
import com.epam.gtc.services.domains.builders.RateDomainBuilderFromEntity;
import com.epam.gtc.web.models.CityModel;
import com.epam.gtc.web.models.RateModel;
import com.epam.gtc.web.models.builders.CityModelBuilder;
import com.epam.gtc.web.models.builders.RateModelBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Context listener.
 */
public class ContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(ContextListener.class);

    @Override
    public final void contextDestroyed(final ServletContextEvent event) {
        log("Servlet context destruction starts");
        // no op
        log("Servlet context destruction finished");
    }

    @Override
    public final void contextInitialized(final ServletContextEvent event) {
        log("Servlet context initialization starts");

        ServletContext servletContext = event.getServletContext();
        initLog4J(servletContext);
        initCommandContainer();
        getCities(servletContext);
        getRates(servletContext);

        log("Servlet context initialization finished");
    }

    /**
     * Initializes log4j framework.
     *
     * @param servletContext servlet context.
     */
    private void initLog4J(final ServletContext servletContext) {
        log("Log4J initialization started");
        try {
            String homeDir = servletContext.getRealPath("/");
            File propertiesFile = new File(homeDir, "WEB-INF/log4j.properties");
            PropertyConfigurator.configure(propertiesFile.toString());
            LOG.debug("Log4j has been initialized");
        } catch (Exception ex) {
            log("Cannot configure Log4j" + ex);
        }
        log("Log4J initialization finished");
    }

    private void initCommandContainer() {

        // initialize commands container
        // just load class to JVM
        try {
            Class.forName(
                    "com.epam.gtc.web.commands.CommandContainer");
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException(
                    "Cannot initialize Command Container");
        }
    }

    private void getCities(ServletContext servletContext) {
        CityService cityService = new CityServiceImpl(new MySQLCityDAOImpl(), new CityEntityBuilder(),
                new CityDomainBuilderFromEntity());
        try {
            List<CityModel> cityModels = new CityModelBuilder().create(cityService.findAll());
            List<String> cityNames = cityModels.stream()
                    .map(cityModel -> cityModel.getName())
                    .collect(Collectors.toList());
            servletContext.setAttribute("cities", cityNames);
            log("all cities has been downloaded to context");
            System.out.println(cityNames);
        } catch (AppException e) {
            log("city downloading is failed");
            log(e.getMessage());
        }
    }

    private void getRates(ServletContext servletContext) {
        RateService rateService = new RateServiceImpl(new MySQLRateDAOImpl(), new RateEntityBuilder(),
                new RateDomainBuilderFromEntity());
        try {
            List<RateModel> rateModels = new RateModelBuilder().create(rateService.findAll());
            servletContext.setAttribute("rates", rateModels);
            log("all rates has been downloaded to context");
            rateModels.stream().forEach(rateModel -> System.out.println(rateModel.getCost()));
        } catch (AppException e) {
            log("rate downloading is failed");
            log(e.getMessage());
        }
    }

    private void log(final String msg) {
        System.out.println("[ContextListener] " + msg);
    }
}