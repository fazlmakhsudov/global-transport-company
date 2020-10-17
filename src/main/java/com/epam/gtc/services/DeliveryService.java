package com.epam.gtc.services;


import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.DeliveryDomain;

import java.util.List;

/**
 * Delivery service interface
 *
 * @author Fazliddin Makhsudov
 */
public interface DeliveryService extends BaseService<DeliveryDomain> {
    /**
     * Counts delivery domains number
     *
     * @return delivery domains number
     *
     * @throws ServiceException exception
     */
    int countAllDeliveries() throws ServiceException;

    /**
     * Counts user delivery domains
     *
     * @param userId user identifier
     * @return list of user delivery domains
     *
     * @throws ServiceException exception
     */
    int countUserDeliveries(int userId) throws ServiceException;

    /**
     * Counts delivery domains with given status
     *
     * @param deliveryStatus delivery status
     * @return number of delivery domains
     *
     * @throws ServiceException exception
     */

    int countDeliveries(DeliveryStatus deliveryStatus) throws ServiceException;

    /**
     * Finds delivery domains from offset and limit number
     *
     * @param page         page
     * @param itemsPerPage itemp per page
     * @return list of delivery domains
     *
     * @throws ServiceException exception
     */
    List<DeliveryDomain> findAll(int page, int itemsPerPage) throws ServiceException;

    /**
     * Finds user delivery domains from offset and limit number
     *
     * @param page         page
     * @param itemsPerPage items per page
     * @param userId       user identifier
     * @return list of delivery domains
     *
     * @throws ServiceException exception
     */
    List<DeliveryDomain> findAll(int page, int itemsPerPage, int userId) throws ServiceException;

    /**
     * Counts delivery domains with certain request identifier
     * @param requestId request identifier
     * @return number of delivery domains
     * @throws ServiceException exception
     */
    int countDeliveriesOfRequest(int requestId) throws ServiceException;

}

