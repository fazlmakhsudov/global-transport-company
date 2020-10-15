package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.CityEntity;
import com.epam.gtc.exceptions.DAOException;

import java.util.List;

/**
 * City DAO interface
 *
 * @author Fazliddin Makhsudov
 */
public interface CityDAO extends BaseDAO<CityEntity> {
    /**
     * Read city entity with given name
     *
     * @param name city enitity name
     * @return city entity
     *
     * @throws DAOException exception
     */
    CityEntity read(final String name) throws DAOException;

    /**
     * Counts number of city entities
     *
     * @return city entities number
     *
     * @throws DAOException exception
     */
    int countAllCities() throws DAOException;

    /**
     * Reads city entities from offset and limit number
     *
     * @param offset start reading point
     * @param limit  limit number
     * @return list of city entities
     *
     * @throws DAOException
     */
    List<CityEntity> readCities(int offset, int limit) throws DAOException;
}
