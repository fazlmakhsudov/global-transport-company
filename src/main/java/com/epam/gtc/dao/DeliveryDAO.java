package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.DeliveryEntity;
import com.epam.gtc.exceptions.DAOException;

import java.util.List;

/**
 * Delivery DAO interface
 *
 * @author Fazliddin Makhsudov
 */
public interface DeliveryDAO extends BaseDAO<DeliveryEntity> {
    /**
     * Counts delivery entities number
     *
     * @return delivery entities number
     *
     * @throws DAOException exception
     */
    int countAllDeliveries() throws DAOException;

    /**
     * Counts user delivery entities
     *
     * @param userId user identifier
     * @return list of user delivery entities
     *
     * @throws DAOException exception
     */
    int countUserDeliveries(int userId) throws DAOException;

    /**
     * Counts delivery entities with given status
     *
     * @param deliveryStatusId delivery status identifier
     * @return number of delivery entities
     *
     * @throws DAOException exception
     */
    int countDeliveries(int deliveryStatusId) throws DAOException;

    /**
     * Reads delivery entities from offset and limit number
     *
     * @param offset start position
     * @param limit  limint number
     * @return list of delivery entities
     *
     * @throws DAOException exception
     */
    List<DeliveryEntity> readDeliveries(int offset, int limit) throws DAOException;

    /**
     * Reads user delivery entities from offset and limit number
     *
     * @param offset start position
     * @param limit  limint number
     * @param userId user identifier
     * @return list of delivery entities
     *
     * @throws DAOException exception
     */
    List<DeliveryEntity> readDeliveries(int offset, int limit, int userId) throws DAOException;

    /**
     * Counts delivery entities with certain request identifier
     *
     * @param requestId request identifier
     * @return number of delivery entities
     *
     * @throws DAOException exception
     */
    int countDeliveriesOfRequest(int requestId) throws DAOException;
}
