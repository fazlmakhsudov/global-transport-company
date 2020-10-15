package com.epam.gtc.services;

import com.epam.gtc.exceptions.ServiceException;

import java.util.List;

/**
 * Base service interface
 *
 * @param <T> domain
 * @author Fazliddin Makhsudov
 */
public interface BaseService<T> {
    /**
     * Adds domain
     *
     * @param item domain
     * @return id of created domain
     *
     * @throws ServiceException exception
     */
    int add(T item) throws ServiceException;

    /**
     * Finds domain with given id
     *
     * @param id identifier of domain
     * @return found domain
     *
     * @throws ServiceException exception
     */
    T find(int id) throws ServiceException;

    /**
     * Saves domain
     *
     * @param item domain
     * @return saved status
     *
     * @throws ServiceException exception
     */
    boolean save(T item) throws ServiceException;

    /**
     * Deletes domain with given identifier
     *
     * @param id domain identifier
     * @return removed status
     *
     * @throws ServiceException exception
     */
    boolean remove(int id) throws ServiceException;

    /**
     * Finds all enities
     *
     * @return list of domains
     *
     * @throws ServiceException exception
     */
    List<T> findAll() throws ServiceException;
}
