package com.epam.gtc.services;


import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.CityDomain;

import java.util.List;

/**
 * City service interface
 *
 * @author Fazliddin Makhsudov
 */
public interface CityService extends BaseService<CityDomain> {
    /**
     * Find city domain with given name
     *
     * @param name city domain name
     * @return city domain
     *
     * @throws ServiceException exception
     */
    CityDomain find(String name) throws ServiceException;

    /**
     * Counts number of city domains
     *
     * @return city domains number
     *
     * @throws ServiceException exception
     */
    int countAllCities() throws ServiceException;

    /**
     * Finds city domains from page and required item number
     *
     * @param page         page
     * @param itemsPerPage number of items
     * @return list of city domains
     *
     * @throws ServiceException exception
     */
    List<CityDomain> findAll(int page, int itemsPerPage) throws ServiceException;
}

