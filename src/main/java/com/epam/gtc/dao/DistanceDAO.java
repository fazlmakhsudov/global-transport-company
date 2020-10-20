package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.DistanceEntity;
import com.epam.gtc.exceptions.DAOException;

import java.util.List;

/**
 * Distance DAO interface
 *
 * @author Fazliddin Makhsudov
 */
public interface DistanceDAO extends BaseDAO<DistanceEntity> {
    /**
     * Counts number of all distance entities
     *
     * @return number of distance enitities
     *
     * @throws DAOException exception
     */
    int countAllDistances() throws DAOException;

    /**
     * Reads distance entities from offset and limit number
     *
     * @param offset start position
     * @param limit  limit number
     * @return list of distance entities
     *
     * @throws DAOException exception
     */
    List<DistanceEntity> readDistances(int offset, int limit) throws DAOException;

    /**
     * Reads distance entity between two city entities
     *
     * @param fromCityId city entity identifier
     * @param toCityId   city entity identifier
     * @return distance entity
     *
     * @throws DAOException exception
     */
    DistanceEntity read(int fromCityId, int toCityId) throws DAOException;

    /**
     * Reads all distance entities with distance less of equal given distance
     *
     * @param distance distance
     * @return list of distances
     *
     * @throws DAOException exception
     */
    List<DistanceEntity> readAll(double distance) throws DAOException;

    /**
     * Reads distance entities for certain city
     *
     * @param fromCityId city entity identifier
     * @return list of distance entities
     *
     * @throws DAOException exception
     */
    List<DistanceEntity> readAll(int fromCityId) throws DAOException;
}
