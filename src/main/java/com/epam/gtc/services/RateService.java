package com.epam.gtc.services;


import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.RateDomain;

import java.util.List;

/**
 * Rate service interface
 *
 * @author Fazliddin Makhsudov
 */
public interface RateService extends BaseService<RateDomain> {
    /**
     * Counts number of all rate domains
     *
     * @return number of rate domains
     *
     * @throws ServiceException exception
     */
    int countAllRates() throws ServiceException;

    /**
     * Finds rate domain with given name
     *
     * @param name rate name
     * @return rate domain
     *
     * @throws ServiceException exception
     */
    RateDomain find(String name) throws ServiceException;

    /**
     * Finds rate domains from page and itemsPerPage number
     *
     * @param page         start position
     * @param itemsPerPage itemsPerPage number
     * @return list of rate domains
     *
     * @throws ServiceException exception
     */
    List<RateDomain> findAll(int page, int itemsPerPage) throws ServiceException;

    /**
     * Finds all rate domains with distance less  given distance
     *
     * @param maxDistance distance
     * @return list of rate domains
     *
     * @throws ServiceException exception
     */
    List<RateDomain> findAll(double maxDistance) throws ServiceException;
}

