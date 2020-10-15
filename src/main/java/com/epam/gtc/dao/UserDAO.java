package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.UserEntity;
import com.epam.gtc.exceptions.DAOException;

import java.util.List;

/**
 * User DAO interface
 *
 * @author Fazliddin Makhsudov
 */
public interface UserDAO extends BaseDAO<UserEntity> {
    /**
     * Reads user entity with given email
     *
     * @param email user email
     * @return user entity
     *
     * @throws DAOException exception
     */
    UserEntity read(final String email) throws DAOException;

    /**
     * Counts number of user entities
     *
     * @return number of user entities
     *
     * @throws DAOException exception
     */
    int countAllUsers() throws DAOException;

    /**
     * Reads user entities from offset and limit number
     *
     * @param offset start position
     * @param limit  limit number
     * @return list of user entities
     *
     * @throws DAOException exception
     */
    List<UserEntity> readUsers(int offset, int limit) throws DAOException;
}
