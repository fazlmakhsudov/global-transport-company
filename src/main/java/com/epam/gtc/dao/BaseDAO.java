package com.epam.gtc.dao;


import com.epam.gtc.exceptions.DAOException;

import java.util.List;

/**
 * Base DAO interface
 *
 * @param <T> entity
 * @author Fazliddin Makhsudov
 */
public interface BaseDAO<T> {
    /**
     * Creates entity
     *
     * @param item entity
     * @return id of created entity
     *
     * @throws DAOException exception
     */
    int create(final T item) throws DAOException;

    /**
     * Reads entity with given id
     *
     * @param id identifier of entity
     * @return found entity
     *
     * @throws DAOException exception
     */
    T read(final int id) throws DAOException;

    /**
     * Updates entity
     *
     * @param item entity
     * @return updated status
     *
     * @throws DAOException exception
     */
    boolean update(final T item) throws DAOException;

    /**
     * Deletes entity with given identifier
     *
     * @param id entity identifier
     * @return deleted status
     *
     * @throws DAOException exception
     */
    boolean delete(final int id) throws DAOException;

    /**
     * Reads all enities
     *
     * @return list of entities
     *
     * @throws DAOException exception
     */
    List<T> readAll() throws DAOException;
}
