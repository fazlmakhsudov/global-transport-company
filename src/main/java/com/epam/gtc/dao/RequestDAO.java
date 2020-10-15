package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.RequestEntity;
import com.epam.gtc.exceptions.DAOException;

import java.util.List;

/**
 * Request DAO interface
 *
 * @author Fazliddin Makhsudov
 */
public interface RequestDAO extends BaseDAO<RequestEntity> {
    /**
     * Counts number of all request entities
     *
     * @return number of request entities
     *
     * @throws DAOException exception
     */
    int countAllRequests() throws DAOException;

    /**
     * Counts number of user user request entities
     *
     * @param userId user entity identifier
     * @return number of request entities
     *
     * @throws DAOException exception
     */
    int countUserRequests(int userId) throws DAOException;

    /**
     * Counts request entities with given status
     *
     * @param requestStatusId status identifier
     * @return number of request identifier
     *
     * @throws DAOException exception
     */
    int countRequests(int requestStatusId) throws DAOException;

    /**
     * Reads request entities from offset and limit number
     *
     * @param offset start position
     * @param limit  limit number
     * @return list of request entities
     *
     * @throws DAOException exception
     */
    List<RequestEntity> readRequests(int offset, int limit) throws DAOException;

    /**
     * Reads user request entities from offset and limit number
     *
     * @param offset start position
     * @param limit  limit number
     * @param userId user identifier
     * @return list of request entities
     *
     * @throws DAOException exception
     */
    List<RequestEntity> readRequests(int offset, int limit, int userId) throws DAOException;
}
