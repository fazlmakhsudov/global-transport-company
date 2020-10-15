package com.epam.gtc.services;


import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.RequestDomain;

import java.util.List;

/**
 * Request service interface
 *
 * @author Fazliddin Makhsudov
 */
public interface RequestService extends BaseService<RequestDomain> {
    /**
     * Counts number of all request domains
     *
     * @return number of request domains
     *
     * @throws ServiceException exception
     */
    int countAllRequests() throws ServiceException;

    /**
     * Counts number of user user request domains
     *
     * @param userId user domain identifier
     * @return number of request domains
     *
     * @throws ServiceException exception
     */
    int countUserRequests(int userId) throws ServiceException;

    /**
     * Counts request domains with given status
     *
     * @param requestStatus status
     * @return number of request identifier
     *
     * @throws ServiceException exception
     */
    int countRequests(RequestStatus requestStatus) throws ServiceException;

    /**
     * Finds request domains from page and itemsPerPage number
     *
     * @param page         start position
     * @param itemsPerPage itemsPerPage number
     * @return list of request domains
     *
     * @throws ServiceException exception
     */
    List<RequestDomain> findAll(int page, int itemsPerPage) throws ServiceException;

    /**
     * Finds user request domains from page and itemsPerPage number
     *
     * @param page         start position
     * @param itemsPerPage itemsPerPage number
     * @param userId       user identifier
     * @return list of request domains
     *
     * @throws ServiceException exception
     */
    List<RequestDomain> findAll(int page, int itemsPerPage, int userId) throws ServiceException;
}

