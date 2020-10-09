package com.epam.gtc.dao;


import com.epam.gtc.exceptions.DAOException;

import java.util.List;

/**
 * Base DAO interface
 *
 * @param <T>
 */
public interface BaseDAO<T> {

    int create(final T item) throws DAOException;

    T read(final int id) throws DAOException;

    boolean update(final T item) throws DAOException;

    boolean delete(final int id) throws DAOException;

    List<T> readAll() throws DAOException;
}
