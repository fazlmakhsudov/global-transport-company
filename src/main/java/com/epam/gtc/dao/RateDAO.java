package com.epam.gtc.dao;

import com.epam.gtc.dao.entities.RateEntity;
import com.epam.gtc.exceptions.DAOException;

import java.util.List;

/**
 * Rate DAO interface
 *
 * @author Fazliddin Makhsudov
 */
public interface RateDAO extends BaseDAO<RateEntity> {
    /**
     * Counts number of all rate entities
     *
     * @return number of rate entities
     *
     * @throws DAOException exception
     */
    int countAllRates() throws DAOException;

    /**
     * Reads rate entity with given name
     *
     * @param name rate name
     * @return rate entity
     *
     * @throws DAOException exception
     */
    RateEntity read(final String name) throws DAOException;

    /**
     * Reads rate entities from offset and limit number
     *
     * @param offset start position
     * @param limit  limit number
     * @return list of rate entities
     *
     * @throws DAOException exception
     */
    List<RateEntity> readRates(int offset, int limit) throws DAOException;
}
