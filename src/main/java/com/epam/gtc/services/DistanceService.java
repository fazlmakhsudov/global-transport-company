package com.epam.gtc.services;


import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.DistanceDomain;

import java.util.List;

/**
 * Distance service interface
 *
 * @author Fazliddin Makhsudov
 */
public interface DistanceService extends BaseService<DistanceDomain> {
    /**
     * Counts number of all distance domains
     *
     * @return number of distance enitities
     *
     * @throws ServiceException exception
     */
    int countAllDistances() throws ServiceException;

    /**
     * Finds distance domains from page and itemsPerPage number
     *
     * @param page         start position
     * @param itemsPerPage itemsPerPage number
     * @return list of distance domains
     *
     * @throws ServiceException exception
     */
    List<DistanceDomain> findAll(int page, int itemsPerPage) throws ServiceException;

    /**
     * Finds distance entity between two city domains
     *
     * @param fromCityId city entity identifier
     * @param toCityId   city entity identifier
     * @return distance entity
     *
     * @throws ServiceException exception
     */
    DistanceDomain find(int fromCityId, int toCityId) throws ServiceException;

    /**
     * Finds all distance domains with distance less of equal given distance
     *
     * @param distance distance
     * @return list of distances
     *
     * @throws ServiceException exception
     */
    List<DistanceDomain> findAll(double distance) throws ServiceException;
}

